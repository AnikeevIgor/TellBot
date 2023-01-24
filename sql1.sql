CREATE TABLE notification_task (
                                   id INTEGER PRIMARY KEY ,
                                   chatId INTEGER,
                                   notificationText TEXT,
                                   DATE DATETIME DEFAULT CURRENT_TIMESTAMP
);