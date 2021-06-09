# @author Schyler Kelsch (2021)

DROP TABLE IF EXISTS webcams;
DROP TABLE IF EXISTS webcam_purposes;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
   userNumber INT NOT NULL UNIQUE AUTO_INCREMENT,
   loginName VARCHAR(50) NOT NULL UNIQUE,
   userPassword VARCHAR(64) NOT NULL,
   firstName VARCHAR(25) DEFAULT '',
   lastName VARCHAR(35) DEFAULT '',
   emailAddress VARCHAR(50) DEFAULT '',
   userRole VARCHAR(30) NOT NULL,
   lastLoginTime DATETIME DEFAULT NOW(), # YYYY-MM-DD HH:MM:SS
   loginCount INT DEFAULT 0, 
   salt VARCHAR(50),
   LastAttemptedLoginTime DATETIME, # YYYY-MM-DD HH:MM:SS
   locked BOOLEAN DEFAULT FALSE,
   AttemptedLoginCount INT DEFAULT 0,
   PRIMARY KEY (userNumber)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE INDEX user_email_index on users (emailAddress);

CREATE TABLE IF NOT EXISTS webcam_purposes (
    purpose VARCHAR(20) NOT NULL UNIQUE,
    PRIMARY KEY (purpose)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS webcams (
    webcamID INT NOT NULL UNIQUE AUTO_INCREMENT,
    userNumber INT NOT NULL,
    webcamName VARCHAR(500) NOT NULL,
    description VARCHAR(500),
    purpose VARCHAR(20),
    url VARCHAR(100), # points to external resource for camera feed
    latitude DOUBLE(12, 10) NOT NULL CONSTRAINT latRange CHECK(latitude >= -90 AND latitude <= 90),
    longitude DOUBLE(13, 10) NOT NULL CONSTRAINT longRange CHECK(longitude >= -180 AND longitude <= 180),
    city VARCHAR(100),
    stateProvinceRegion VARCHAR(105),
    country VARCHAR(100),
    height FLOAT(7, 3) CONSTRAINT heightPos CHECK(height >= 0),
    heightUnits VARCHAR(2) CONSTRAINT heightUnits CHECK(heightUnits IN('ft', 'mi', 'm', 'km')),
    azimuth INT CONSTRAINT azRange CHECK(azimuth >= -1 AND azimuth < 360), # -1 means 360 degree field of view
    fieldOfView INT CONSTRAINT fieldRange CHECK(fieldOfView >= 0 AND fieldOfView <= 360),
    rotating VARCHAR(30), # static/rotating
    rotatingFieldOfView INT CONSTRAINT rotRange CHECK(rotatingFieldOfView >= 0 AND rotatingFieldOfView < 360),
    verticalViewAngle INT DEFAULT 0 CONSTRAINT vertViewAngleRange CHECK(verticalViewAngle >= -90 AND verticalViewAngle <= 90),
    verticalFieldOfView INT CONSTRAINT vertFieldRange CHECK(verticalFieldOfView >= 0 AND verticalFieldOfView < 360),
    minViewRadius INT DEFAULT 0 CONSTRAINT minViewPos CHECK(minViewRadius >= 0),
    minViewRadiusUnits VARCHAR(2) CONSTRAINT minViewUnits CHECK(minViewRadiusUnits IN('ft', 'mi', 'm', 'km')),
    maxViewRadius INT CONSTRAINT maxViewPos CHECK(maxViewRadius >= 0),
    maxViewRadiusUnits VARCHAR(2) CONSTRAINT maxViewUnits CHECK(maxViewRadiusUnits IN('ft', 'mi', 'm', 'km')),
    webcamColor CHAR(6),
    viewshedColor CHAR(6),
    isActive BOOLEAN DEFAULT TRUE,
    dateSubmitted DATETIME DEFAULT NOW(), # YYYY-MM-DD HH:MM:SS
    dateApproved DATETIME, # YYYY-MM-DD HH:MM:SS
    approvalStatus VARCHAR(20) DEFAULT 'submitted' CONSTRAINT approvalStatusValues CHECK(approvalStatus IN('submitted', 'approved', 'revoked')),
    PRIMARY KEY (webcamID),
    FOREIGN KEY (userNumber) REFERENCES users(userNumber),
    FOREIGN KEY (purpose) REFERENCES webcam_purposes(purpose)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS error_logs;
CREATE TABLE IF NOT EXISTS error_logs (
  `EVENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EVENT_DATE` datetime DEFAULT NULL,
  `LEVEL` varchar(15) DEFAULT NULL,
  `LOGGER` varchar(60) DEFAULT NULL,
  `MSG` varchar(240) DEFAULT NULL,
  `THROWABLE` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`EVENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS properties;
CREATE TABLE IF NOT EXISTS properties (
   propertyNumber INT NOT NULL UNIQUE AUTO_INCREMENT,
   propertyName VARCHAR(64) NOT NULL UNIQUE,
   propertyValue VARCHAR(128) NOT NULL,
   description VARCHAR(1024) DEFAULT '',
   previousValue VARCHAR(128),
   defaultValue VARCHAR(128),
   PRIMARY KEY (propertyNumber)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO users (loginName, userPassword, firstName, lastName, emailAddress,
userRole, loginCount, salt)
VALUES 
    # admin1234
    ('admin', '1b22b95d03da1e7811dd0be2f8801e12a119a2c105f26a5bef18651f298e1121', 'System', 'Administrator', 'cjones@bloomu.edu', 'SystemAdmin', 0, 'me9ehohb79iobanooq0etdnc7q');

INSERT INTO users(loginName, userPassword, firstName, lastName, emailAddress,
userRole, loginCount, salt)
VALUES
    # Viewshed2021
    ('jbrunski', '12f045cff2a7d6f1b02029241105a299b86e7a25607fa7be09bdb884a41c0315', 'Jeffrey', 'Brunskill', 'jbrunski@bloomu.edu', 'SystemAdmin', 0, 'mi0r9vqgu4s14ckc268qa6c6m3');

INSERT INTO users(loginName, userPassword, firstName, lastName, emailAddress,
userRole, loginCount, salt)
VALUES
    # WindyWebcams
    ('WindyUser', '8617b4d8dd6cdb68377f76570c9adb294f0c2e421826dce656209fddfdaf834b', 'Windy', 'User', 'jbrunski@bloomu.edu', 'SystemAdmin', 0, '4qtko7rqj4p1sglefq1k3ori9u');

INSERT INTO users (loginName, userPassword, firstName, lastName, emailAddress,
userRole, loginCount, salt)
VALUES 
	# SunriseWebcams
    ('SunriseUser', 'b1030fe1c5e4124ab4b4505e9d071fdbdeba4fe41d0c98687af549b470e4e3e5', 'Sunrise', 'User', 'jbrunski@bloomu.edu', 'SystemAdmin', 0, 'l5o0rjfeq6frsgqbkpcgisa7r6');

INSERT INTO users(loginName, userPassword, firstName, lastName, emailAddress,
userRole, loginCount, salt)
VALUES
    # kmjviewshed
    ('kmj83477', 'da157c5600e6bd9170f81d50db05b18bdff1bc4fa131cbe52f6e102eb99d33b8', 'Karissa', 'Jelonek', 'kmj83477@huskies.bloomu.edu', 'SystemAdmin', 0, '2ruqf7rqatcce94q1hin4nu5tv');

INSERT INTO users(loginName, userPassword, firstName, lastName, emailAddress,
userRole, loginCount, salt)
VALUES
    # SchylerKelschPassword
    ('sk45893', '6912991d887b951d657b3a35368533e755a35f490b8168399ecd79602d66be77', 'Schyler', 'Kelsch', 'sk45893@huskies.bloomu.edu', 'SystemAdmin', 0, 'h27r7dh46c38vqdmtgnv1lsj0s');

INSERT INTO users(loginName, userPassword, firstName, lastName, emailAddress,
userRole, loginCount, salt)
VALUES
    ('mw20758', 'ed36656b849cf9bac67ef62a96895187d3703700a4c7592b959e3e550a873c0b', 'Mason', 'Wu', 'mw20758@huskies.bloomu.edu', 'SystemAdmin', 0, 'mksbkjp7oc0dvjr633rm5emh60');

INSERT INTO users(loginName, userPassword, firstName, lastName, emailAddress,
userRole, loginCount, salt)
VALUES
    # hello,password
    ('mpo98217', '314e89edece054685667610919a9538815039821cd41b738b992ffd04b78950a', 'Michael', 'O\'Donnell', 'mpo98217@huskies.bloomu.edu', 'SystemAdmin', 0, 'biiam49osbbp29g14uv0efnsp5');

INSERT INTO properties(propertyName, propertyValue, description, previousValue, defaultValue)
VALUES
	('TimeUpdated', '02:00:00', 'This is what time the webcams table will be updated with Windy.com\'s latest webcams.', '02:00:00', '02:00:00');

INSERT INTO properties(propertyName, propertyValue, description, previousValue, defaultValue) 
VALUES
	('ESRI API Key', 'AAPKa50fb832e69a4973bdde988bb814f678-4QTyxvEY8q8TpLdiegAb8votin63a53Jo9E49a-U4iur8Z7Ar9FeMyPP0IINeQr', 'The API key used to access ESRI\'s map and viewshed tools', 'AAPKa50fb832e69a4973bdde988bb814f678-4QTyxvEY8q8TpLdiegAb8votin63a53Jo9E49a-U4iur8Z7Ar9FeMyPP0IINeQr', 'AAPKa50fb832e69a4973bdde988bb814f678-4QTyxvEY8q8TpLdiegAb8votin63a53Jo9E49a-U4iur8Z7Ar9FeMyPP0IINeQr');

INSERT INTO properties(propertyName, propertyValue, description, previousValue, defaultValue)
VALUES
	('x-windy-key', 'YdBxQkF8a5KCt20f0ZjrLQEQ3v9rJZme', 'The API key used to access Windy.com\'s list of webcams', 'YdBxQkF8a5KCt20f0ZjrLQEQ3v9rJZme', 'YdBxQkF8a5KCt20f0ZjrLQEQ3v9rJZme');

# uncomment to fetch sunrise webcams from sunriseWebcams.txt (tab-delimited text file)
# load data local infile <path to sunriseWebcams.txt> into table webcams fields terminated by '\t' lines terminated by '\r\n' ignore 1 lines;

# uncomment to fetch webcam purposes from webcamPurposes.txt (tab-delimited text file)
# load data local infile <path to webcamPurposes.txt> into table webcam_purposes fields terminated by '\t' lines terminated by '\r\n' ignore 1 lines;
