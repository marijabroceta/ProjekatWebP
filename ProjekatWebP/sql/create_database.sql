DROP SCHEMA IF EXISTS ProjekatYouTube;
CREATE SCHEMA ProjekatYouTube DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE ProjekatYouTube;

CREATE TABLE users (
	userName VARCHAR(15) NOT NULL,
    userPassword VARCHAR(15) NOT NULL,
    firstName VARCHAR(15),
    lastName VARCHAR(15),
    email VARCHAR(30) NOT NULL,
    channelDescription VARCHAR(100),
    role ENUM ('USER','ADMIN') NOT NULL DEFAULT 'USER',
    dateOfRegistration DATE NOT NULL,
    blocked BOOLEAN NOT NULL DEFAULT FALSE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (userName)
);

INSERT INTO users(userName,userPassword,firstName,lastName,email,role,dateOfRegistration) VALUES ('janko','janko123','Janko','Jankovic','janko@gmail.com','USER','2017-11-5');
INSERT INTO users(userName,userPassword,firstName,lastName,email,role,dateOfRegistration) VALUES ('zivko','zivko123','Zivko','Zivkovic','zivko@gmail.com','USER','2017-12-6');
INSERT INTO users(userName,userPassword,firstName,lastName,email,role,dateOfRegistration) VALUES ('nikola','nikola123','Nikola','Nikolic','nikola@gmail.com','USER','2017-10-8');
INSERT INTO users(userName,userPassword,firstName,lastName,email,role,dateOfRegistration) VALUES ('marko','marko123','Marko','Markovic','marko@gmail.com','ADMIN','2017-11-3');

CREATE TABLE subscribe ( 
	mainUser VARCHAR(15),
    subscriber VARCHAR(15),
    FOREIGN KEY (mainUser) REFERENCES users(userName) ON DELETE RESTRICT,
    FOREIGN KEY (subscriber) REFERENCES users(userName) ON DELETE RESTRICT
);

INSERT INTO subscribe(mainUser,subscriber) VALUES ('janko','zivko');
INSERT INTO subscribe(mainUser,subscriber) VALUES ('zivko','nikola');

CREATE TABLE video(
	id BIGINT AUTO_INCREMENT,
    videoUrl VARCHAR(100) NOT NULL,
    videoPicture VARCHAR(100) NOT NULL,
    videoName VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    visibility ENUM ('PRIVATE','PUBLIC','UNLISTED') NOT NULL,
    enableComments BOOLEAN NOT NULL DEFAULT TRUE,
    enableRating BOOLEAN DEFAULT TRUE,
    numberOfLikes BIGINT NOT NULL,
    numberOfDislikes BIGINT NOT NULL,
    numberOfViews BIGINT NOT NULL,
    postDate DATE NOT NULL,
    ownerUserName VARCHAR(15),
    blocked BOOLEAN NOT NULL DEFAULT FALSE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (ownerUserName) REFERENCES users(userName) ON DELETE RESTRICT
);

INSERT INTO video(videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted)
VALUES ('https://www.youtube.com/embed/6ZfuNTqbHE8','images/thumbnail.png','Marvel Studios Avengers:Infinity War',' Avengers: Infinity War. In theaters May 4.','PUBLIC',true,true,0,0,154,'2017-11-29','janko',false,false);
INSERT INTO video(videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted)
VALUES ('https://www.youtube.com/embed/NPoHPNeU9fc','images/thumbnail.png','Marvels Avengers Assemble',' Avengers Assemble. In theaters May 4.','PUBLIC',true,true,0,0,132,'2017-11-6','janko',false,false);
INSERT INTO video(videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted)
VALUES ('https://www.youtube.com/embed/u1OKBqHICMQ','images/thumbnail.png','Avengers: Age of Ultron',' Avengers: Age of Ultron. In theaters May 4.','PUBLIC',true,true,0,0,167,'2017-11-13','janko',false,false);
INSERT INTO video(videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted)
VALUES ('https://www.youtube.com/embed/1TVhYrbGqzA','images/thumbnail.png','Marvels Thor: Ragnarok',' Watch the official trailer for Marvels "Thor: Ragnarok"','PUBLIC',true,true,0,0,146,'2017-12-9','zivko',false,false);
INSERT INTO video(videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted)
VALUES ('https://www.youtube.com/embed/8hYlB38asDY','images/thumbnail.png','Iron Man 1',' Genre: Action','PUBLIC',true,true,0,0,178,'2017-10-25','nikola',false,false);
INSERT INTO video(videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted)
VALUES ('https://www.youtube.com/embed/BoohRoVA9WQ','images/thumbnail.png','Iron Man 2','Iron Man 2 Trailer','PRIVATE',true,true,0,0,178,'2017-11-10','nikola',false,false);
INSERT INTO video(videoUrl,videoPicture,videoName,description,visibility,enableComments,enableRating,numberOfLikes,numberOfDislikes,numberOfViews,postDate,ownerUserName,blocked,deleted)
VALUES ('https://www.youtube.com/embed/oYSD2VQagc4','images/thumbnail.png','Iron Man 3','Iron Man 3 is an American superhero film','UNLISTED',true,true,0,0,178,'2017-11-19','nikola',false,false);

CREATE TABLE comments(
	id BIGINT AUTO_INCREMENT,
    content VARCHAR(200),
    dateOfComment DATE NOT NULL,
    ownerUserName VARCHAR(15),
    videoId BIGINT NOT NULL,
    numberOfLikes BIGINT NOT NULL,
    numberOfDislikes BIGINT NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (ownerUserName) REFERENCES users(userName) ON DELETE RESTRICT,
    FOREIGN KEY (videoId) REFERENCES video(id) ON DELETE RESTRICT
);

INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('Great movie','2017-12-25','janko',4,0,0);
INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('Fantastic movie','2017-12-28','nikola',4,0,0);
INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('I love this movie so much','2017-12-10','zivko',4,0,0);
INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('Great movie','2017-11-14','janko',5,0,0);
INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('The best movie ever','2017-12-19','zivko',5,0,0);
INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('I love this movie so much','2017-10-26','nikola',5,0,0);
INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('Great movie','2017-10-26','nikola',7,0,0);
INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('Fantastic movie','2017-11-15','janko',7,0,0);
INSERT INTO comments(content,dateOfComment,ownerUserName,videoId,numberOfLikes,numberOfDislikes)
VALUES ('The best movie I ever seen','2017-12-24','zivko',7,0,0);

CREATE TABLE likeDislike (
	id BIGINT AUTO_INCREMENT,
    liked BOOLEAN NOT NULL,
    dateOfLike DATE NOT NULL,
    ownerUserName VARCHAR(15),
    PRIMARY KEY (id),
    FOREIGN KEY (ownerUserName) REFERENCES users(userName) ON DELETE RESTRICT
);

INSERT INTO likeDislike(liked,dateOfLike,ownerUserName) VALUES(true,'2017-11-14','janko');
INSERT INTO likeDislike(liked,dateOfLike,ownerUserName) VALUES(true,'2017-12-16','zivko');
INSERT INTO likeDislike(liked,dateOfLike,ownerUserName) VALUES(false,'2017-11-14','janko');
INSERT INTO likeDislike(liked,dateOfLike,ownerUserName) VALUES(false,'2017-10-14','nikola');
INSERT INTO likeDislike(liked,dateOfLike,ownerUserName) VALUES(true,'2017-12-18','zivko');

CREATE TABLE likeDislikeVideo (
	likeId BIGINT,
    videoId BIGINT,
    FOREIGN KEY (likeId) REFERENCES likeDislike (id) ON DELETE RESTRICT,
    FOREIGN KEY (videoId) REFERENCES video (id) ON DELETE RESTRICT
);

INSERT INTO likeDislikeVideo(likeId,videoId) VALUES (1,4);
INSERT INTO likeDislikeVideo(likeId,videoId) VALUES (2,1);

CREATE TABLE likeDislikeComment (
	likeId BIGINT,
    commentId BIGINT,
    FOREIGN KEY (likeId) REFERENCES likeDislike (id) ON DELETE RESTRICT,
    FOREIGN KEY (commentId) REFERENCES comments(id) ON DELETE RESTRICT
);

INSERT INTO likeDislikeComment (likeId,commentId) VALUES (3,4);
INSERT INTO likeDislikeComment (likeId,commentId) VALUES (4,1);
INSERT INTO likeDislikeComment (likeId,commentId) VALUES (5,2);