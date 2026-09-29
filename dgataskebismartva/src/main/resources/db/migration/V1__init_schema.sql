
CREATE TABLE IF NOT EXISTS task_statuses (
                                             id SERIAL PRIMARY KEY,
                                             code VARCHAR(50) NOT NULL UNIQUE
    );


CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


CREATE TABLE IF NOT EXISTS projects (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


CREATE TABLE IF NOT EXISTS tasks (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(150) NOT NULL,
    description TEXT,
    status_id INT NOT NULL REFERENCES task_statuses(id),
    assigned_user_id INT REFERENCES users(id) ON DELETE SET NULL,
    project_id INT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    due_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


INSERT INTO task_statuses (id, code) VALUES
 (1, 'TO_DO'),
 (2, 'IN_PROGRESS'),
 (3, 'REVIEW'),
 (4, 'DONE')
ON CONFLICT (id) DO NOTHING;


SELECT setval('task_statuses_id_seq', (SELECT MAX(id) FROM task_statuses));

--  satesto momxmareblebi
INSERT INTO users (first_name, last_name, email) VALUES
('Elena', 'Bliadze', 'elena@example.com'),
('Nika', 'Shermadini', 'nika@example.com'),
('Giorgi', 'Bakhtrionidze', 'giorgi@example.com'),
('Mariam', 'Dolenjishvili', 'mariam@example.com'),
('Natalia', 'Vardosanidze', 'natalia@example.com')
ON CONFLICT (email) DO NOTHING;

-- satesto proeqtebi
INSERT INTO projects (name, description) VALUES
('Task 1', 'Shevadginot arqitekturad'),
('Task 2', 'Shevitanot tsvlilebebi bazashi'),
('Task 3', 'Davamatot funktsionali'),
('Task 4', 'IOS sistemistvis gankutvnili programa davamatod');

--  satesto davalebebit shevseba
INSERT INTO tasks (title, description, status_id, assigned_user_id, project_id, due_date) VALUES
('Task 1', 'Arqitekruashi ganvsazghvtrot versiebi da shevitanot axali punqtebi', 4, 1, 1, '2026-09-05 18:00:00'),
('Task 2', 'Tsavshalot ramodenime chanaweri bazidan', 3, 2, 2, '2026-09-10 18:00:00'),
('Task 3', 'Vinaidan gvakli tsashlis funqktsia, davamatod axla', 3, 4, 3, '2026-09-15 18:00:00');