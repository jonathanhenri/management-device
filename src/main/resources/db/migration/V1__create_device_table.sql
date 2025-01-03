CREATE TABLE IF NOT EXISTS device (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    create_time TIMESTAMP NOT NULL,
    CONSTRAINT unique_name_brand UNIQUE (name, brand)
);
