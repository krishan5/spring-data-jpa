insert into course(name) values('Spring boot course');
insert into course(name) values('Spring JPA course');
insert into course(name) values('Spring Webflux course');

insert into review(course_id, rating, description) values(1, 4, 'Explained with real examples with easy explanation.');
insert into review(course_id, rating, description) values(1, 5, 'Well covered topic in this course.');
insert into review(course_id, rating, description) values(3, 4, 'Well explained but more of theoretical knowledge sharing instead of practical.');

insert into passport(number) values ('Z235P3421');
insert into passport(number) values ('K521A5224');
insert into passport(number) values ('A958U2307');
insert into passport(number) values ('D000D1234');

insert into student(name, passport_id) values('Recker', 1);
insert into student(name, passport_id) values('Nikolai', 2);
insert into student(name, passport_id) values('Raiden', 3);
insert into student(name, passport_id) values('Delete Me', 4);