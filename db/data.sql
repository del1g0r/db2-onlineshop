INSERT INTO product (creation_date, name, price) values (CURRENT_TIMESTAMP, 'Product 1', 1000);
INSERT INTO product (creation_date, name, price) values (CURRENT_TIMESTAMP, 'Product 2', 1100);

INSERT INTO "user" (name, pswhash, group_name) values ('guest', crypt('', 'db2_onlineshop'), 'GUEST');
INSERT INTO "user" (name, pswhash, group_name) values ('user', crypt('user', 'db2_onlineshop'), 'USER');
INSERT INTO "user" (name, pswhash, group_name) values ('admin', crypt('admin', 'db2_onlineshop'), 'ADMIN');

COMMIT;