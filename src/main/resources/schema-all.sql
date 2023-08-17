CREATE TABLE if not exists accounts (
  accountid int primary key AUTO_INCREMENT,
  balance decimal(10, 2) CHECK (balance > 0)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8mb4 COMMENT='账户表';

CREATE TABLE if not exists oprecord  (
  id int primary key AUTO_INCREMENT,
  accountid int references accounts(accountid),
  opmoney decimal(10, 2),
  optime datetime,
  optype enum('deposite','withdraw','transfer') NOT NULL,
  transferid varchar(50)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8mb4 COMMENT='操作记录表';

commit;