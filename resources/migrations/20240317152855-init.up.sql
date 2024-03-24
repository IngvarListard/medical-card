CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT
);
--;;

CREATE TABLE documents (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    "path" TEXT,
    "type" TEXT,
    document JSONB,
    user_id INT,
    event_id INT,
    doctor_id INT,
    report_date DATETIME DEFAULT (datetime('now')),
    created_at DATETIME DEFAULT (datetime('now')),
    updated_at DATETIME DEFAULT (datetime('now')),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (event_id) REFERENCES events(id),
	FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
--;;

CREATE TABLE doctors (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    created_at DATETIME DEFAULT (datetime('now')),
    updated_at TEXT DEFAULT (datetime('now'))
);
--;;

CREATE TABLE specializations (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT,
    display_name TEXT
);
--;;

CREATE TABLE events (
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	"type" TEXT,
    name TEXT NOT NULL,
    description TEXT,
    event_type_id INT,
	created_at DATETIME DEFAULT (datetime('now')) NOT NULL,
	updated_at DATETIME DEFAULT (datetime('now')) NOT NULL,
	FOREIGN KEY (event_type_id) REFERENCES event_types(id)
);
--;;

CREATE TABLE event_types (
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    display_name TEXT NOT NULL
);
--;;

CREATE TABLE doctors_to_organizations (
	doctor_id INT NOT NULL,
	organization_id INT NOT NULL,
	PRIMARY KEY (doctor_id, organization_id),
	FOREIGN KEY (doctor_id) REFERENCES doctors(id),
	FOREIGN KEY (organization_id) REFERENCES organizations(id)
);
--;;

-- CREATE TABLE doctors_to_documents (
--     doctor_id INT NOT NULL,
--     document_id INT NOT NULL,
--     user_id INT,
--     event_id INT,
-- 	PRIMARY KEY (doctor_id, document_id),
-- 	FOREIGN KEY (doctor_id) REFERENCES doctors(id),
-- 	FOREIGN KEY (document_id) REFERENCES documents(id),
--     CONSTRAINT one_of_dtd CHECK (
--         NULLIF(user_id, '') IS NULL OR NULLIF(event_id, '') IS NULL
--     )
-- );

CREATE TABLE doctors_to_specializations (
    doctor_id INT NOT NULL,
    specialization_id INT NOT NULL,
	PRIMARY KEY (doctor_id, specialization_id),
	FOREIGN KEY (doctor_id) REFERENCES doctors(id),
	FOREIGN KEY (specialization_id) REFERENCES specializations(id)
);
--;;
