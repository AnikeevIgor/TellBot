-- liquibase formatted sql

-- changeset anikeev:1
CREATE TABLE notification_task (
 id INTEGER PRIMARY KEY ,
 chatId INTEGER,
 notificationText TEXT,
 notificationDate TIMESTAMP
)