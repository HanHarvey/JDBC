/*
    Writing Group:  GroupName, HeadWriter, YearFormed, Subject
    Book         :  BookTitle, YearPublished, NumberPages
    Publishers   :  PubName, PubAddress, PubPhone, PubEmail
*/

CREATE TABLE writingGroup
(
  groupName  VARCHAR(20)    NOT NULL,
  headWriter VARCHAR(20)    NOT NULL,
  yearFormed VARCHAR(5)       NOT NULL,
  subject    VARCHAR(20)    NOT NULL,
  CONSTRAINT pk_writingGroup PRIMARY KEY (groupName)
);

CREATE TABLE book
(
  bookTitle VARCHAR(20) NOT NULL,
  yearPublished VARCHAR(5) NOT NULL,
  numberPages   VARCHAR(5) NOT NULL,
  groupName  VARCHAR(20)    NOT NULL,       /* Foreign Key */
  pubName VARCHAR(20) NOT NULL,             /* Foreign Key */
  CONSTRAINT pk_book PRIMARY KEY (GroupName, BookTitle)
);

CREATE TABLE publishers
(
  pubName VARCHAR(20) NOT NULL,
  pubAddress  VARCHAR(20) NOT NULL,
  pubPhone    VARCHAR(20) NOT NULL,
  pubEmail    VARCHAR(20) NOT NULL,
  CONSTRAINT pk_publishers PRIMARY KEY (pubName)
);

INSERT INTO writingGroup VALUES('Gryffindors', 'Harry', '2017', 'Fantasy');
INSERT INTO writingGroup VALUES('writingLife', 'Tom', '2014', 'Drama');

INSERT INTO publishers VALUES('Steve Smith', '123 Rose St.', '555-555-5555', 'steve@gmail.com');
INSERT INTO book VALUES('ICodeGood','2015','451','writingLife','Steve Smith');

DROP TABLE writingGroup;
DROP TABLE book;
DROP TABLE publishers;