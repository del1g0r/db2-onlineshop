INSERT INTO product (creation_date, name, price) values (CURRENT_TIMESTAMP, 'Product 1', 1000);
INSERT INTO product (creation_date, name, price) values (CURRENT_TIMESTAMP, 'Product 2', 1100);

INSERT INTO "user" (name, salt, pswhash, group_name) SELECT 'guest', t.md5, crypt('', t.md5), 'GUEST' FROM (SELECT gen_salt('md5') md5) t;
INSERT INTO "user" (name, salt, pswhash, group_name) SELECT 'user', t.md5, crypt('user', t.md5), 'USER' FROM (SELECT gen_salt('md5') md5) t;
INSERT INTO "user" (name, salt, pswhash, group_name) SELECT 'admin', t.md5, crypt('admin', t.md5), 'ADMIN' FROM (SELECT gen_salt('md5') md5) t;

COMMIT;