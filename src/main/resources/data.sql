REPLACE INTO `roles`
VALUES (1, 'ADMIN'),
       (2, 'USER');

--admin user
REPLACE INTO `users`
VALUES (1,1,'admin','admin','admin','$2a$10$PhtB2sSCLGHRdDVJ/6/vrunFNz2p5oLy7Khr87FrgzQudikvPrgE.');

REPLACE INTO `user_role`
VALUES (1,1),(1,2);

--user
REPLACE INTO `users`
VALUES (2,1,'user','user','user','$2a$10$0ACmaDjVFsfKDyxr2gsi4ODiL.M0LDqgqvbNbijOn47A4hizY0pNe');

REPLACE INTO `user_role` VALUES (2,2);