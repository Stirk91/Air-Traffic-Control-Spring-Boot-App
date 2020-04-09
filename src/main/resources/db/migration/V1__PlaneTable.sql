CREATE TABLE plane (
    plane_id UUID NOT NULL,
    tail_number VARCHAR(10) NOT NULL,
    state       VARCHAR(10) NOT NULL,
    last_action TIMESTAMP(0) NOT NULL DEFAULT now(),
    distance    INT,
    altitude    INT,
    speed       INT,
    heading     INT,
    PRIMARY KEY (plane_id),
    UNIQUE (tail_number)
);

CREATE TABLE Plane_FlightNumber (
    plane_id      UUID        NOT NULL,
    flight_number VARCHAR(6) NOT NULL UNIQUE,
    PRIMARY KEY (plane_id, flight_number),
    FOREIGN KEY (plane_id) REFERENCES Plane (plane_id)
);

CREATE TABLE Runway (
    runway_id   INT PRIMARY KEY,
    runway_name VARCHAR(10) NOT NULL,
    plane_id    UUID,
    FOREIGN KEY (plane_id) REFERENCES Plane (plane_id)
);

CREATE TABLE Runway_RunwayDesignation (
    runway_id          INT        NOT NULL,
    runway_designation VARCHAR(3) NOT NULL,
    PRIMARY KEY (runway_id, runway_designation),
    FOREIGN KEY (runway_id) REFERENCES Runway (runway_id)
);

CREATE TABLE Taxiway (
	taxiway_id INT PRIMARY KEY,
	taxiway_name VARCHAR(2) NOT NULL,
	plane_id UUID,
	FOREIGN KEY (plane_id) REFERENCES Plane(plane_id)
);

CREATE TABLE Runway_Taxiway (
    runway_id  INT NOT NULL,
    taxiway_id INT NOT NULL,
    PRIMARY KEY (runway_id, taxiway_id),
    FOREIGN KEY (runway_id) REFERENCES Runway (runway_id),
    FOREIGN KEY (taxiway_id) REFERENCES Taxiway (taxiway_id)
);


CREATE SEQUENCE Gate_seq;

CREATE TABLE Gate (
    gate_id   INT DEFAULT NEXTVAL ('Gate_seq') PRIMARY KEY,
    gate_name VARCHAR(4) NOT NULL,
    plane_id  UUID,
    FOREIGN KEY (plane_id) REFERENCES Plane (plane_id)
);

CREATE TABLE Taxiway_Gate (
    taxiway_id INT NOT NULL,
    gate_id    INT NOT NULL,
    PRIMARY KEY (taxiway_id, gate_id),
    FOREIGN KEY (taxiway_id) REFERENCES Taxiway (taxiway_id),
    FOREIGN KEY (gate_id) REFERENCES Gate (gate_id)
);

