CREATE TABLE IF NOT EXISTS `crud_demo`.`product` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `name` VARCHAR(45) NOT NULL,
                                       `price` FLOAT NULL,
                                       PRIMARY KEY (`id`),
                                       UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                       UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);