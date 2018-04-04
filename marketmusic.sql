create table artists
(
artistID int not null auto_increment,
artistName varchar(255),
primary key (artistID)
);

create table albums
(
albumID int not null auto_increment,
albumTitle varchar(255),
artistID int,
price double,
quantity int,
primary key (albumID),
foreign key (artistID) references artists(artistID)
);

insert into artists (artistName) values ('Beatles');
insert into artists (artistName) values ('Rolling Stones');
insert into artists (artistName) values ('David Bowie');
insert into artists (artistName) values ('Pink Floyd');

insert into albums (albumTitle, artistID, price, quantity) values ('Sgt Pepper',1,7.5,10);
insert into albums (albumTitle, artistID, price, quantity) values ('White album',1,12.5,2);
insert into albums (albumTitle, artistID, price, quantity) values ('Brown Sugar',2,7.5,5);
insert into albums (albumTitle, artistID, price, quantity) values ('Sticky Fingers',2,9.5,6);
insert into albums (albumTitle, artistID, price, quantity) values ('Let it Bleed',2,7.5,3);
insert into albums (albumTitle, artistID, price, quantity) values ('Hunky Dory',3,8.5,10);
insert into albums (albumTitle, artistID, price, quantity) values ('Scary Monsters',3,8.5,6);
insert into albums (albumTitle, artistID, price, quantity) values ('Let s Dance',3,7.5,12);
insert into albums (albumTitle, artistID, price, quantity) values ('Dark Side of the Moon',4,10.5,10);
insert into albums (albumTitle, artistID, price, quantity) values ('Wish you were here',4,9.5,3);


