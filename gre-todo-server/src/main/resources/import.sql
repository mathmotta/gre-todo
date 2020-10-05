INSERT INTO building (name) VALUES  ('Valhalla'),  ('Bifröst'), ('Fólkvangr'), ('Himinbjörg'), ('Bilskírnir');

INSERT INTO person (name) VALUES ('Odin'), ('Freyja'), ('Thor'),('Loki'), ('Heimdall');

INSERT INTO project (name, project_status, created_at, updated_at, created_by, updated_by, building_id, person_id) VALUES
    ('Cleanup the garbage left on Bifröst', 'NEW', NOW(), NOW(),'DefaultGreUser', 'DefaultGreUser', 2, 3),
    ('Assist Thor in his garbage cleaning mission', 'NEW', NOW(), NOW(),'DefaultGreUser', 'DefaultGreUser', 2, 5),
    ('Meeting about construction work at Bifröst', 'NEW', NOW(), NOW(),'DefaultGreUser', 'DefaultGreUser', 1, 1),
    ('Meeting about construction work at Bifröst', 'NEW', NOW(), NOW(),'DefaultGreUser', 'DefaultGreUser', 1, 5);

