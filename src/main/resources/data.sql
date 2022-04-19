CREATE TABLE vessels (
    id      INT        AUTO_INCREMENT  PRIMARY KEY,
    code    VARCHAR(8) NOT NULL
);

CREATE TABLE equipments (
    id          INT             AUTO_INCREMENT  PRIMARY KEY ,
    code        VARCHAR(8)      NOT NULL                    ,
    vessel_id   INT             NOT NULL                    ,
    name        VARCHAR(256)    NOT NULL                    ,
    location    VARCHAR(256)    NOT NULL                    ,
    active      INT             NOT NULL                    ,
    FOREIGN KEY (vessel_id)     REFERENCES vessels (id)
);

CREATE TABLE operations (
    id              INT             AUTO_INCREMENT  PRIMARY KEY ,
    equipment_id    INT             NOT NULL                    ,
    type            VARCHAR(256)    NOT NULL                    ,
    cost            DECIMAL(12, 2)  NOT NULL                    ,
    FOREIGN KEY (equipment_id)      REFERENCES equipments (id)
);
