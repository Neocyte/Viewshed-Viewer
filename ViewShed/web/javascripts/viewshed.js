/*
 * Dynamic events for the embedded ArcGIS Viewshed Tool.
 *
 * @author Mason Wu
 */

// Load the esri modules
// Syntax: require([moduleArray], function(moduleObjects) {});
require([
    "esri/Map",
    "esri/views/SceneView",
    "esri/layers/GraphicsLayer",
    "esri/layers/FeatureLayer",
    "esri/Graphic",
    "esri/geometry/Point",
    "esri/geometry/geometryEngine",
    "esri/tasks/Geoprocessor",
    "esri/tasks/support/FeatureSet",
    "esri/widgets/Search",
    "esri/widgets/Search/SearchSource",
    "esri/request",
    "dojo/on",
    "esri/config",
    "esri/geometry/Polygon",
    "esri/geometry/support/webMercatorUtils",
    "esri/geometry/geometryEngine"
],

// -------------------------------------------Main Function---------------------------------------------

/**
 * Main function that uses the esri modules
 */
function (Map, SceneView, GraphicsLayer, FeatureLayer, Graphic, Point, geometryEngine, Geoprocessor, FeatureSet, Search, SearchSource, esriRequest, on, esriConfig, Polygon, webMercatorUtils, geometryEngine) {
    
    // Create the map
    const map = new Map({
        basemap: "hybrid",
        ground: "world-elevation"
    });

    // Create the scene view and pass the map into it
    const view = new SceneView({
        container: "viewDiv",
        map: map,
        camera: {
            position: [
                -95.7129,   // longitude
                37.0902,    // latitude
                30000000    // zoom in meters
            ],
            tilt: 0
        }
    });

    // Create a feature layer for webcam searching
    const featureLayerWebcams = new FeatureLayer({
            source: []
    });

    // Define the search widget for the webcams.
    const webcamSearch = new SearchSource({
        placeholder: "Search Webcams Here",
        name: "Webcams",
        layer: featureLayerWebcams,
        getSuggestions: params => {
            return esriRequest(document.URL.replace("/index.html", "") + "/SearchWebcams", {
                query: {
                    limit: 6,
                    query: params.suggestTerm.replace(/ /g, "+"),
                    submitted: false
                },
                responseType: "json"
            }).then(results => {
                return results.data.data.map(result => {
                    return {
                        key: "name",
                        text: result.webcamName,
                        sourceIndex: params.sourceIndex,
                        webcamID: result.webcamID
                    };
                });
            });
        },
        getResults: params => {
            console.log(params);
            return esriRequest(document.URL.replace("/index.html", "") + "/SearchWebcams", {
                query: {
                    query: params.suggestResult.webcamID,
                    submitted: true
                },
                responseType: "json"
            }).then(results => {
                return results.data.data.map(result => {
                    let graphic = new Graphic({
                        geometry: new Point({
                            x: result.longitude,
                            y: result.latitude
                        }),
                        attributes: result
                    });

                    let buffer = geometryEngine.geodesicBuffer(graphic.geometry, 1000, "meters");
                    
                    // Initialize the webcam search popups
                    webcamSearch.popupTemplate = {
                        title: `${result.webcamName}`,
                        content: [{
                                type: "text",
                                text: `
                                       <div>Description: ${result.description}</div>
                                           <div>Purpose: ${result.purpose}</div>
                                           <div>URL: <a href="${result.url}">${result.url}</a></div>
                                           <br>
                                           <div>City: ${result.city}</div>
                                           <div>State/Region: ${result.stateProvinceRegion}</div>
                                           <div>Country: ${result.country}</div>
                                           <br>
                                           <div>Latitude: ${result.latitude}</div>
                                           <div>Longitude: ${result.longitude}</div>
                                           <div>Observer Height: ${result.height} ${result.heightUnits}</div>
                                           <div>Azimuth: ${result.azimuth}</div>
                                           <div>FOV: ${result.fieldOfView}</div>
                                           <div>View Radius: ${result.maxViewRadius} ${result.maxViewRadiusUnits}</div>
                                       `
                        }]
                    };

                    return {
                        extent: buffer.extent,
                        feature: graphic,
                        name: result.webcamName
                    };
                });
            });
        }
    });

    // Initialize the search widget
    const searchWidget = new Search({
        view: view,
        container: document.querySelector(".search-bar"),
        sources: [webcamSearch]
    });

    // Create a graphics layer for WEBCAM markers and pass it to the map
    const webcamGraphicsLayer = new GraphicsLayer ({
        elevationInfo: "relative-to-ground"
    });
    map.add(webcamGraphicsLayer);

    // Create a graphics layer for CALCULATOR markers and pass it to the map
    const calculatorGraphicsLayer = new GraphicsLayer ({
        elevationInfo: "relative-to-ground"
    });
    map.add(calculatorGraphicsLayer);

    // Create the marker symbol
    const markerSymbol = {
        type: "simple-marker",
         color: [255, 0, 0],
         outline: {
             color: [255, 255, 255],
             width: 2
         }
   }; 
   
    // Create a viewshed color
    const fillSymbol = {
        type: "simple-fill",
        color: [226, 119, 40, 0.75], // will be dynamically changed when computing viewshed
        outline: {
            color: [255, 255, 255],
            width: 1
        }
    };
    
    // Create a FOV polygon color
    const fillFOV= {
        type: "simple-fill",
        color: [255, 255, 255, 0.75],
        outline: {
            color: [255, 255, 255],
            width: 1
        }
    };

    // Initiate the geoprocessor
    const gpUrl = "https://elevation.arcgis.com/arcgis/rest/services/Tools/Elevation/GPServer/Viewshed";
    const gp = new Geoprocessor(gpUrl);
    gp.outSpatialReference = {
        wkid: 102100
    };

    // On clicking the scene view, add a marker
    const viewHandler = on.pausable(view, "click", addMarker);
    
    // On pointer-move, test if the pointer is on top of a marker
    // If yes, pause the view handler; otherwise resume the view handler
    // This ensures that clicking a marker for a popup does not also add a marker
    view.on("pointer-move", function (event) { 
        view.hitTest(event).then(function (response) { 
            if (response.results.length) { 
                response.results.filter(function (result) { 
                    // Check if the graphic is a marker (circle)
                    return (result.graphic.symbol.style === "circle");
                })[0];
                viewHandler.pause();
                document.getElementById("viewDiv").style.cursor = "pointer";
            } else { 
                viewHandler.resume();
                document.getElementById("viewDiv").style.cursor = "default";
            } 
        }); 
    }); 

    // -------------------------------------------Fetch Webcams---------------------------------------------

    window.addEventListener("load", function() {
        
       // Set the API key
       esriConfig.apiKey = esriAPIKey;
   
        // Get the webcam database URL
        url = (document.URL + "/ViewWebcams?hi=hello").replace("/index.jsp", "");

        // Initiate a fetch request to the webcam database URL
        fetch(url)

            // If the response is not okay (404), throw an error
            // Otherwise return the json file that contains the webcams
            .then(response => {
                if (!response.ok) {
                    throw new Error("Could not reach website at URL: " + document.URL);
                }
                return response.json();
            })

            // Then for each webcam in the json.data array,
            // Create a point, add the point to the WEBCAM graphics layer, and submit the job using webcam parameters
            .then(json => {
                json.data.forEach(webcam => {
                    
                    // Create point
                    const point = new Point({
                        longitude: webcam.longitude,
                        latitude: webcam.latitude
                    });
                    
                    // Create graphic out of the point
                    const inputGraphic = new Graphic({
                        geometry: point,
                        symbol: markerSymbol,
                        popupTemplate: {
                            title: `${webcam.webcamName}`,
                            content: [{
                                    type: "text",
                                    text: `
                                           <div>Description: ${webcam.description}</div>
                                           <div>Purpose: ${webcam.purpose}</div>
                                           <div>URL: <a href="${webcam.url}">${webcam.url}</a></div>
                                           <br>
                                           <div>City: ${webcam.city}</div>
                                           <div>State/Region: ${webcam.stateProvinceRegion}</div>
                                           <div>Country: ${webcam.country}</div>
                                           <br>
                                           <div>Latitude: ${webcam.latitude}</div>
                                           <div>Longitude: ${webcam.longitude}</div>
                                           <div>Observer Height: ${webcam.height} ${webcam.heightUnits}</div>
                                           <div>Azimuth: ${webcam.azimuth}</div>
                                           <div>FOV: ${webcam.fieldOfView}</div>
                                           <div>View Radius: ${webcam.maxViewRadius} ${webcam.maxViewRadiusUnits}</div>
                                           `
                            }]
                        }
                    });
                      
                    // Add the graphic to the WEBCAM graphics layer
                    webcamGraphicsLayer.add(inputGraphic);
                    
                    // if the FOV is greater than 0, continue making the viewshed
                    if (webcam.fieldOfView > 0) {
                        
                        // Store the graphic as a feature to be passed into the geoprocessor
                        const featureSet = new FeatureSet({
                            features: [inputGraphic]
                        });

                        // Parse the max radius unit from the respective webcam value in the database
                        unitR = null;
                        switch(webcam.maxViewRadiusUnits) {
                           case "mi":
                                unitR = "Miles";
                                break;
                           case "ft":
                                unitR = "Feet";
                                break;
                           case "km":
                                   unitR = "Kilometers";
                                   break;
                           case "m":
                                   unitR = "Meters";
                                   break;
                           default:
                                    unitR = "Miles";
                         }

                        // Parse the height unit from the respective webcam value in the database
                        unitH = null;
                        switch(webcam.heightUnits) {
                           case "mi":
                                unitH = "Miles";
                                break;
                           case "ft":
                                unitH = "Feet";
                                break;
                           case "km":
                                   unitH = "Kilometers";
                                   break;
                           case "m":
                                   unitH = "Meters";
                                   break;
                           default:
                                    unitH = "Miles";
                         }

                        // Store the viewshed parameters
                        const params = {
                            InputPoints: featureSet,
                            MaximumDistance: parseFloat(webcam.maxViewRadius),
                            MaximumDistanceUnits: unitR,
                            ObserverHeight: parseFloat(webcam.height),
                            ObserverHeightUnits: unitH
                          };

                        // The geoprocessor will process our parameters, then PROMISE to draw the results
                        gp.submitJob(params).then((jobSubmitInfo) => {   
                                const jobId = jobSubmitInfo.jobId;           
                                gp.waitForJobCompletion(jobId).then((jobCompletedInfo) => {
                                    console.info('Viewshed successful: ', jobCompletedInfo.jobStatus);            
                                    gp.getResultData(jobId,'OutputViewshed').then(({value}) => {
                                        drawResultData_noGoTo(value);
                                    })

                                    // Next, draw the intersection between the viewshed graphics and the FOV graphics
                                    .then(function() {
                                        draw_FOV_intersection(webcam.longitude, webcam.latitude, parseFloat(webcam.maxViewRadius), unitR, webcam.azimuth, webcam.fieldOfView, "webcamGraphicsLayer");
                                    });
                                })
                                
                                .catch(function(err) {
                                    console.log(`[${webcam.webcamName}] may have failed:\nLongitude: ${webcam.longitude}\nLatitude: ${webcam.latitude}`);
                                    console.log(err);
                                });
                        });
                        
                    // Otherwise, the this webcam's FOV is 0 so just log the webcam in the console
                    } else {
                        console.log(`The FOV of ${webcam.webcamName} is 0, so it has no viewshed.`);
                    }
                });
            })

            .catch(err => console.error(err));
    });

    // -------------------------------------------Helper Functions---------------------------------------------
    
    // Global variable to store the first marker created on click
    // This will be used to remove the first marker after calculating a viewshed at the same location
    var point_to_remove = null;
    
    // Global variable to store the viewshed polygon graphic
    // This will be used to create the intersection polygon between the viewshed polygon and the FOV polygon
    var viewshed_polygonGraphic = null;
    
    /**
     * Adds a marker on click
     * @param {type} event the click event
     */
    function addMarker(event) {
        
        // Only add a marker when the calculator is open
        const calculator = document.querySelector("#calculator");
        if (calculator.style.display === "block") {
            let lat = document.querySelector(".input-lat");
            let long = document.querySelector(".input-long"); 
            
            // Add the marker
            const point = new Point({
                longitude: event.mapPoint.longitude,
                latitude: event.mapPoint.latitude
            });
            
            // Give an arbitrary attribute for identification and removal purposes
            point.toRemove = true;
            
            // Change marker color to black to differentiate from webcam markers
            markerSymbol.color = [0, 0, 0];
            
            // Add the marker as a graphic in the calculator graphics layer
            const inputGraphic = new Graphic({
                geometry: point,
                symbol: markerSymbol
            });
            
            // This marker will be removed in the future
            point_to_remove = inputGraphic;
            
            // Add the graphic to the CALCULATOR graphics layer
            calculatorGraphicsLayer.add(inputGraphic);
            
             // Change the calculator inputs based on the clicked location
            lat.value = event.mapPoint.latitude;
            long.value = event.mapPoint.longitude;
            
            // Change street view
            changeStreetView(event.mapPoint.latitude, event.mapPoint.longitude);
        }
    }

    /**
     * Adds a marker using the given x and y
     * Then passes all function arguments into the geoprocessor to compute the viewshed
     * @param {type} x the x-coordiante of the marker
     * @param {type} y the y-coordinate of the marker
     * @param {type} maxDistance - the desired maximum view radius of the viewshed
     * @param {type} elevation - the observer height
     * @param {type} maxDistanceUnits - the units of maximum view radius
     * @param {type} elevationUnits - the units observer height
     * @param {type} azimuth - the angle the webcam is facing
     * @param {type} fov - the webcam's field of view
     */
    function computeViewshed(x, y, maxDistance, elevation, maxDistanceUnits, elevationUnits, azimuth, fov) {
        
        // Only compute viewshed when the calculator is open
        const calculator = document.querySelector("#calculator");
        if (calculator.style.display === "block") {
            
            // if the FOV is 0, STOP
            if (fov === 0) {
                setTimeout(function() { 
                    alert("The FOV was 0, so no viewshed was generated."); 
                }, 3000);
                return;
            }
            
            // Get the input values that will be passed to the popup
            let lat_input = document.querySelector(".input-lat").value;
            let long_input = document.querySelector(".input-long").value;
            let distance_input = document.querySelector(".input-distance").value;
            let elevation_input = document.querySelector(".input-elevation").value;
            let azimuth_input = document.querySelector(".input-azimuth").value;
            let fov_input = document.querySelector(".input-fov").value;
            let distance_input_unit = document.querySelector("#distanceUnits").value.toLowerCase();
            let elevation_input_unit = document.querySelector("#elevationUnits").value.toLowerCase();
                
            // Add a marker at (x, y)
            point = new Point({
                longitude: x,
                latitude: y
            });
            
            // Change marker color to black to differentiate from webcam markers
            markerSymbol.color = [0, 0, 0];

            // Change street view
            changeStreetView(y, x);
             
            // Add the marker as a graphic in the calculator graphics layer
            const inputGraphic = new Graphic({
                geometry: point,
                symbol: markerSymbol,
                popupTemplate: {
                title: "Custom Marker",
                 content: [{
                         type: "text",
                         text: `
                                <div>Latitude: ${lat_input}</div>
                                <div>Longitude: ${long_input}</div>
                                <div>View Radius: ${distance_input} ${distance_input_unit}</div>
                                <div>Observer Height: ${elevation_input} ${elevation_input_unit}</div>
                                <div>Azimuth: ${azimuth_input}</div>
                                <div>FOV: ${fov_input}</div>
                                `
                 }]
                }
            });
            
            // Add the graphic to the CALCULATOR graphics layer
            calculatorGraphicsLayer.add(inputGraphic);
            
            // Remove the marker that came from clicking
            calculatorGraphicsLayer.remove(point_to_remove);
            point_to_remove = null;

            // Store the graphic as a feature to be passed into the geoprocessor
            const featureSet = new FeatureSet({
                features: [inputGraphic]
            });

            // Store the parameters
            const params = {
                InputPoints: featureSet,
                MaximumDistance: parseFloat(maxDistance),
                MaximumDistanceUnits: maxDistanceUnits,
                DEMResolution: "FINEST",
                ObserverHeight: parseFloat(elevation),
                ObserverHeightUnits: elevationUnits
              };

            /**
             * Progress Bar
             */
            const progressBar = document.querySelector('.progress');
            const progressNum = document.querySelector(".progress-num");
            let current = 0;

            // Repeating timer which updates every 1 millisecond
            const updater = setInterval(() => {
                if (current >= 100) {
                    clearInterval(updater);
                } else if (current >= 50) {
                    progressNum.style.color = "black";
                }

                const updateAmount = 0.25;
                current += updateAmount;
                progressBar.style.width = current + "%";
                progressNum.innerHTML = parseInt(current).toString() + "%";
            },1);

            // Display the progress bar
            progressBar.style.visibility = "visible";

            // The geoprocessor will process our parameters, then PROMISE to draw the results
            gp.submitJob(params).then((jobSubmitInfo) => {   
                    const jobId = jobSubmitInfo.jobId;         
                    gp.waitForJobCompletion(jobId).then((jobCompletedInfo) => {
                        console.info('Viewshed successful: ', jobCompletedInfo.jobStatus);            
                        gp.getResultData(jobId,'OutputViewshed').then(({value}) => {
                            drawResultData(value);
                        })
                        
                       // Next, draw the intersection between the viewshed graphics and the FOV graphics
                      .then(function() {
                          draw_FOV_intersection(x, y, maxDistance, maxDistanceUnits, azimuth, fov, "calculatorGraphicsLayer");
                      })
                        
                      // On resolve, clear the updater and max the progress bar
                      .then(function() {
                          clearInterval(updater);
                          progressNum.style.color = "black";
                          progressBar.style.width = "100%";
                          progressNum.innerHTML = "100%";

                          // After 1 second, reset the progress bar
                          setTimeout(function() {
                              progressBar.style.visibility = "hidden";
                              progressBar.style.width = "0";
                              progressNum.style.color = "white";
                          }, 1000);
                      });
                    })
                    
                    .catch(function(e) {
                          alert("The viewshed is too small!\nTry using a larger view radius.");
                          console.log(e);
                          clearInterval(updater);
                          progressBar.style.visibility = "hidden";
                          progressBar.style.width = "0";
                          progressNum.style.color = "white";
                          calculatorGraphicsLayer.remove(inputGraphic);
                      });
            });
        }
    }

    /**
     * Draws the viewshed graphics obtained from the geoprocessor
     * Then forces the camera to go to the viewshed
     * @param {type} value the viewshed graphics returned from the geoprocessor
     */
    function drawResultData(value) {

        // Get the processed results from the geoprocessor
        const resultFeatures = value.features;

        // Change the symbol color to a random color
        setRandomColor();

        // Assign each resulting graphic a symbol
        const viewshedGraphics = resultFeatures.map(function (feature) {
            feature.symbol = fillSymbol;
            return feature;
        });
        
        // Set the global variable to help the intersection logic in computeViewshed()
        viewshed_polygonGraphic = viewshedGraphics[0];

        // Add the resulting graphics to the CALCULATOR graphics layer
        calculatorGraphicsLayer.addMany(viewshedGraphics);

        // Force the scene view to jump to the viewshed
        view.goTo({
            target: viewshedGraphics,
            tilt: 0
        })

            .catch(function (error) {
                if (error.name !== "AbortError") {
                    console.error(error);
                }
            });
    }
    
     /**
      * Same as drawResultData, but does not force the camera to go to the viewshed
      * @param {type} value the viewshed graphics returned from the geoprocessor
      */
    function drawResultData_noGoTo(value) {

        // Get the processed results from the geoprocessor
        const resultFeatures = value.features;

        // Change the symbol color to a random color
        setRandomColor();

        // Assign each resulting graphic a symbol
        const viewshedGraphics = resultFeatures.map(function (feature) {
            feature.symbol = fillSymbol;
            return feature;
        });
        
        // Set the global variable to help the intersection logic in computeViewshed()
        viewshed_polygonGraphic = viewshedGraphics[0];

        // Add the resulting graphics to the WEBCAMS graphics layer
        webcamGraphicsLayer.addMany(viewshedGraphics);
    }
    
    /**
     * Draws the intersection between the viewshed graphics and the FOV graphics
     * @param {type} x the x-coordinate of the marker
     * @param {type} y the y-coordinate of the marker
     * @param {type} maxDistance the max view radius
     * @param {type} maxDistanceUnits the units of the max view radius
     * @param {type} azimuth the angle the webcam is facing
     * @param {type} fov the webcam's field of view
     * @param {type} layer the layer to add the FOV intersection polygon
     */
    function draw_FOV_intersection(x, y, maxDistance, maxDistanceUnits, azimuth, fov, layer) {
        
        // Set the distance input based on the given layer
        // If we are adding to the calculator layer, the distance input is obtained from the calculator
        // If we are adding to the webcam layer, the distance input is obtained from the maxDistance parameter
        distance_input = null;
        
        if (layer === "calculatorGraphicsLayer") {
            distance_input = document.querySelector(".input-distance").value;
        } else if (layer === "webcamGraphicsLayer") {
            distance_input = maxDistance;
        }
        
        // Convert the max distance to km for the getVertex() function
        switch(maxDistanceUnits) {
        case "Miles":
            maxDistance = distance_input * 1.609;
            break;
       case "Feet":
            maxDistance = distance_input / 3281;
            break;
       case "Meters":
            maxDistance = distance_input / 1000;
            break;
       case "Kilometers":
            break;
       }

       // Calculate the FOV polygon parameters
       let left_angle = azimuth - (fov / 2);
       let right_angle = azimuth + (fov / 2);
       let tempVertexArray = [];
       let realVertexArray = [];
       x = parseFloat(x);
       y = parseFloat(y);

       // Create tangent vertices
       for (let i = left_angle; i <= azimuth; i += 0.5) {
               tempVertexArray.push(getVertex(y, x, maxDistance, i));
       }
       for (let i = azimuth; i <= right_angle; i += 0.5) {
               tempVertexArray.push(getVertex(y, x, maxDistance, i));
       }

       // The first vertex is the origin
       realVertexArray.push([x, y, 500]);

       // The next set of vertices is the tangent vertexes
       tempVertexArray.forEach((vertex) => {
               realVertexArray.push([vertex[1], vertex[0], 500]);
       });

      // The last vertex is the origin
      realVertexArray.push([x, y, 500]);

       // Create the FOV polygon
       const FOV_polygon = new Polygon ({
           rings: realVertexArray,
           spatialReference: {
               wkid: 102100
           }
       });

       // Store the FOV polygon in a graphic and add it to the specified graphics layer
       const FOV_polygonGraphic = new Graphic ({
           geometry: webMercatorUtils.geographicToWebMercator(FOV_polygon),
           symbol: fillFOV
       });

       if (layer === "calculatorGraphicsLayer") {
           calculatorGraphicsLayer.add(FOV_polygonGraphic);
       } else if (layer === "webcamGraphicsLayer") {
           webcamGraphicsLayer.add(FOV_polygonGraphic);
       }

       // Create a polygon from the intersection of the viewshed and the FOV polygon
       const intersection_polygon = geometryEngine.intersect(viewshed_polygonGraphic.geometry, FOV_polygonGraphic.geometry);
       
       const intersection_polygonGraphic = new Graphic ({
         geometry: intersection_polygon,
         symbol: fillSymbol
       });

       // Add the intersection polygon
       // Remove the other polygons
       if (layer === "calculatorGraphicsLayer") {
           calculatorGraphicsLayer.add(intersection_polygonGraphic);
           calculatorGraphicsLayer.remove(viewshed_polygonGraphic);
           calculatorGraphicsLayer.remove(FOV_polygonGraphic);
       } else if (layer === "webcamGraphicsLayer") {
           webcamGraphicsLayer.add(intersection_polygonGraphic);
           webcamGraphicsLayer.remove(viewshed_polygonGraphic);
           webcamGraphicsLayer.remove(FOV_polygonGraphic);
       }
    }
    
    /**
     * Calculates the terminal coordinate given a starting coordinate, distance, and bearing
     * This is used to calculate the terminal coordinates of the FOV polygon
     * @param {type} latitude the x value of the starting coordinate
     * @param {type} longitude the y value of the starting coordinate
     * @param {type} distance the distance from the starting coordinate in km
     * @param {type} bearing the angle from the starting coordiante in degrees
     * @returns {Array} an array containing the x and y of the terminal coordinate
     */
    function getVertex(latitude, longitude, distance, bearing) {
          let R = 6378.1; // radius of the Earth
          let brng = bearing * Math.PI / 180;  // convert bearing to radian
          let lat = latitude * Math.PI / 180;    // current coordinates to radians
          let lon = longitude * Math.PI / 180;

          // Calculate
          lat = Math.asin(Math.sin(lat) * Math.cos(distance / R) + Math.cos(lat) *  Math.sin(distance / R) * Math.cos(brng));
          lon += Math.atan2(Math.sin(brng) * Math.sin(distance / R) * Math.cos(lat), Math.cos(distance/R)-Math.sin(lat)*Math.sin(lat));

          // Coordinates back to degrees and return
          return [lat * 180 / Math.PI, lon * 180 / Math.PI];
    }

    /**
     * Changes the street view in the street view widget
     * @param {type} lat the desired latitude of the street view
     * @param {type} long the desired longitude of the street view
     */
    function changeStreetView(lat, long) {
        const streetView = document.querySelector("#streetview");
        lat = parseFloat(lat).toFixed(6);
        long = parseFloat(long).toFixed(6);
        streetView.setAttribute("src", "https://www.google.com/maps/embed/v1/streetview?key=AIzaSyDZKXc347ZwGPp0beJDLVH9ZZcId91Sw9o&location=" + lat + "," + long + "&heading=0&pitch=0&fov=90");
    }

    /**
     * Sets a random color for the viewshed
     */
    function setRandomColor() {
        let array = [];
        for (let i = 0; i < 3; i++) { // fill the first 3 indexes with random rgb values
            array.push(Math.floor(Math.random() * 255));
        }
        array.push(0.50); // add the opacity as the last index
        fillSymbol.color = array;
    }
    
    /**
     * Clear Viewsheds Button
     */
    (function clearButton() {
        const clearButton = document.querySelector(".nav-1");
        clearButton.onclick = function() {
            calculatorGraphicsLayer.removeAll();
            document.querySelector(".input-lat").value = "";
            document.querySelector(".input-long").value = "";
            document.querySelector(".input-distance").value = "";
            document.querySelector(".input-elevation").value = "";
            document.querySelector(".input-azimuth").value = "";
            document.querySelector(".input-fov").value = "";
        };
    }) ();

    /**
     * Viewshed Calculator
     */
    (function viewshedCalculator () {
        const submitButton = document.querySelector(".input-submit");

        // On clicking the submit button...
        submitButton.onclick = function () {

            // Get the input values
            let lat = document.querySelector(".input-lat").value;
            let long = document.querySelector(".input-long").value;
            let maxDistance = document.querySelector(".input-distance").value;
            let elevation = document.querySelector(".input-elevation").value;
            let azimuth = parseFloat(document.querySelector(".input-azimuth").value);
            let fov = parseFloat(document.querySelector(".input-fov").value);
            let maxDistanceUnits = document.querySelector("#distanceUnits").value;
            let elevationUnits = document.querySelector("#elevationUnits").value;
            let maxDistance_inputField = document.querySelector(".input-distance");
                   
            // Change the input limits based on the units chosen
            // The universal limit of the API is 50 km
            switch(maxDistanceUnits) {
                case "Miles":
                    maxDistance_inputField.setAttribute("max", "31");
                    if (maxDistance > 31) {
                        return;
                    }  
                    break;
                case "Feet":
                    maxDistance_inputField.setAttribute("max", "164041");
                    if (maxDistance > 164041) {
                        return;
                    }  
                    break;
                case "Meters":
                    maxDistance_inputField.setAttribute("max", "50000");
                    if (maxDistance > 50000) {
                        return;
                    }  
                    break;
                case "Kilometers":
                    maxDistance_inputField.setAttribute("max", "50");
                    if (maxDistance > 50) {
                        return;
                    }  
            }

            // If input validation fails, STOP
            if (lat === "" || lat > 90 || lat < -90 || 
                long === "" || long > 180 || long < -180 ||
                maxDistance === "" || maxDistance < 1 ||
                elevation === "" || elevation > 1000 || elevation < 0 ||
                azimuth === "" || azimuth > 360 || azimuth < 0 ||
                fov === "" || fov > 360 || fov < 0) 
            {
                return;
            }

            // Set the camera position to the input values
            view.camera.position.latitude = lat;
            view.camera.position.longitude = long;
            view.camera.position.z = 1000;

            // Force the scene view to jump to the new camera position
            // Then compute the viewshed
            view.goTo({
                target: view.camera,
                tilt: 0
            })

                .then(function() {
                   computeViewshed(long, lat, maxDistance, elevation, maxDistanceUnits, elevationUnits, azimuth, fov);
                })

                .catch(function (error) {
                    if (error.name !== "AbortError") {
                        console.error(error);
                    }
                });
        };
    }) ();
});
