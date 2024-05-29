CREATE TABLE IF NOT EXISTS Run (
    id INT NOT NULL,
    title VARCHAR(256) NOT NULL,
    started_on TIMESTAMP NOT NULL,
    finished_on TIMESTAMP NOT NULL,
    kilometers INT NOT NULL,
    location VARCHAR(10) NOT NULL,
    PRIMARY KEY (id)
);