# Java_AccountingNote

管理員
INSERT INTO UserInfo (Account, PWD, Name, Email, UserLevel, CreateDate) VALUES (admin, 12345678, 管理員1.0, admin@ubay.tw, 0, 2022-01-04)

一般會員
INSERT INTO UserInfo (Account, PWD, Name, Email, UserLevel, CreateDate) VALUES (test, 12345678, 測試員1.0, test@gmail.com, 1, 2022-01-04)

刪除會員後的log會自動生成在根資料夾內，檔名為UserDelete.log
