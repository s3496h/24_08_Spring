###(INIT 시작)
# DB 세팅
DROP DATABASE IF EXISTS `24_08_Spring`;
CREATE DATABASE `24_08_Spring`;
USE `24_08_Spring`;

# 게시글 테이블 생성
CREATE TABLE article(
      id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
      regDate DATETIME NOT NULL,
      updateDate DATETIME NOT NULL,
      title CHAR(100) NOT NULL,
      `body` TEXT NOT NULL
);

# 회원 테이블 생성
CREATE TABLE `member`(
      id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
      regDate DATETIME NOT NULL,
      updateDate DATETIME NOT NULL,
      loginId CHAR(30) NOT NULL,
      loginPw CHAR(100) NOT NULL,
      `authLevel` smallint(2) unsigned default 3 comment '권한 레벨 (3=일반,7=관리자)',
      `name` char(20) not null,
      nickname char(20) not null,
      cellphoneNum char(20) not null,
      email char(50) not null,
      delStatus tinyint(1) unsigned not null default 0 comment '탈퇴 여부 (0=탈퇴 전, 1=탈퇴 후)',
      delDate datetime comment '탈퇴 날짜'
);



## 게시글 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목4',
`body` = '내용4';


## 회원 테스트 데이터 생성
## (관리자)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`authLevel` = 7,
`name` = '관리자',
nickname = '관리자',
cellphoneNum = '01012341234',
email = 'abc@gmail.com';

## (일반)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '회원1_이름',
nickname = '회원1_닉네임',
cellphoneNum = '01043214321',
email = 'abcd@gmail.com';

## (일반)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '회원2_이름',
nickname = '회원2_닉네임',
cellphoneNum = '01056785678',
email = 'abcde@gmail.com';

alter table article add column memberId int(10) unsigned not null after updateDate;

update article
set memberId = 2
where id in (1,2);

update article
set memberId = 3
where id in (3,4);


# 게시판(board) 테이블 생성
CREATE TABLE board (
      id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
      regDate DATETIME NOT NULL,
      updateDate DATETIME NOT NULL,
      `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항) free(자유) QnA(질의응답) ...',
      `name` CHAR(20) NOT NULL UNIQUE COMMENT '게시판 이름',
      delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제 여부 (0=삭제 전, 1=삭제 후)',
      delDate DATETIME COMMENT '삭제 날짜'
);

## 게시판(board) 테스트 데이터 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'NOTICE',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'FREE',
`name` = '자유';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'QnA',
`name` = '질의응답';

alter table article add column boardId int(10) unsigned not null after `memberId`;

UPDATE article
SET boardId = 1
WHERE id IN (1,2);

UPDATE article
SET boardId = 2
WHERE id = 3;

UPDATE article
SET boardId = 3
WHERE id = 4;

###(INIT 끝)
##########################################
SELECT *
FROM article
ORDER BY id DESC;


SELECT *
FROM board;


SELECT *
FROM `member`;


###############################################################################


## 게시글 테스트 데이터 대량 생성
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
select now(), now(), floor(RAND() * 2) + 2, FLOOR(RAND() * 3) + 1, CONCAT('제목__', RAND()), CONCAT('내용__', RAND())
from article;


select floor(RAND() * 2) + 2

SELECT FLOOR(RAND() * 3) + 1


INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목__', RAND()),
`body` = CONCAT('내용__', RAND());

show full columns from `member`;
desc `member`;