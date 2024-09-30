CREATE DATABASE IF NOT EXISTS `todo` DEFAULT CHARACTER SET utf8mb4;
USE `todo`;

DROP TABLE IF EXISTS `task`;

CREATE TABLE `task`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `description` varchar(100) NOT NULL,
    `status`      int(11)      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `task` (`id`, `description`, `status`)
VALUES (1, 'Complete project documentation', 1),
       (2, 'Fix bugs in task manager', 0),
       (3, 'Prepare presentation for meeting', 1),
       (4, 'Review pull request #42', 2),
       (5, 'Update user authentication module', 1),
       (6, 'Plan sprint backlog', 0),
       (7, 'Refactor database queries', 1),
       (8, 'Test new API endpoint', 2),
       (9, 'Schedule performance reviews', 0),
       (10, 'Research new frontend framework', 1),
       (11, 'Write unit tests for task service', 2),
       (12, 'Optimize image loading on website', 0),
       (13, 'Deploy new version of app to server', 1),
       (14, 'Backup database', 2),
       (15, 'Analyze customer feedback', 0),
       (16, 'Design new landing page', 1),
       (17, 'Refactor CSS and styles', 2),
       (18, 'Organize team meeting for project update', 0),
       (19, 'Create feature roadmap for Q4', 1),
       (20, 'Fix issue with task deletion', 2);