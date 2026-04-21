CREATE DATABASE IF NOT EXISTS art_gallery;
USE art_gallery;


CREATE TABLE role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name ENUM('ADMIN', 'USER') NOT NULL
);

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (role_id) REFERENCES role(id)
);


CREATE TABLE artist (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    birth_year INT,
    death_year INT,
    nationality VARCHAR(100)
);

CREATE TABLE exhibition (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    is_active TINYINT(1) DEFAULT 0,
    artist_id INT,
	capacity INT NOT NULL DEFAULT 20,
    reserved_tickets INT NOT NULL DEFAULT 0,
    FOREIGN KEY (artist_id) REFERENCES artist(id) ON DELETE SET NULL
);


CREATE TABLE ticket_category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE booking (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    total_price DECIMAL(10,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    exhibition_id INT NOT NULL,
    ticket_category_id INT NOT NULL,
    quantity INT NOT NULL,
    price_per_ticket DECIMAL(10,2) NOT NULL,
    reservation_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id)
        REFERENCES booking(id)
        ON DELETE CASCADE,
    FOREIGN KEY (ticket_category_id)
        REFERENCES ticket_category(id)
        ON DELETE CASCADE
);
