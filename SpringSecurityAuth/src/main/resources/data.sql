insert into account values (1,'email1@gmail.com',1,  'marc', 'mon','$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'ROLE_ADMIN');
insert into account values (2,'email2@gmail.com',1,  'aur', 'man','$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'ROLE_USER');
insert into account values (3,'email3@gmail.com',1,  'bidule', 'tru','$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'ROLE_USER');
insert into account values (4,'email4@gmail.com',1,  'machin', 'tru','$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'ROLE_USER');

insert into connection values (1,2);
insert into connection values (2,1);
insert into connection values (1,3);
insert into connection values (4,1);

insert into transaction values (1, 100,  "truc",  null, "BANK", 1 , null);
