CREATE TABLE IF NOT EXISTS user (
    ID INT(5) AUTO_INCREMENT PRIMARY KEY,
    mail VARCHAR(50),
    name VARCHAR(20),
    surnames VARCHAR(40),
    password VARCHAR(20),
    photo TEXT
);

CREATE TABLE IF NOT EXISTS audio (
    ID INT(5) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200),
    duration TIME,
    path TEXT
)

CREATE TABLE IF NOT EXISTS playlist (
    ID INT(5) AUTO_INCREMENT PRIMARY KEY,
    user INT(5),
    FOREIGN KEY (user) REFERENCES user(ID)
);

CREATE TABLE IF NOT EXISTS playlistAudioRelation (
    ID INT(5) AUTO_INCREMENT PRIMARY KEY,
    playlist INT(5),
    audio INT(5),
    FOREIGN KEY (playlist) REFERENCES playlist (ID),
    FOREIGN KEY (audio) REFERENCES audio (ID)
);

INSERT INTO user VALUES(null, 'chrisfidare@yahoo.com', 'Chris', 'Fidalgo Areste', '123456789', null);