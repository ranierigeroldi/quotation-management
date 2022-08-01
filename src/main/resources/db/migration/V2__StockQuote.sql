CREATE TABLE IF NOT EXISTS `stock_quote` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `stock_operation_id` BINARY(16) NOT NULL,
    `date` DATE NOT NULL,
    `price` NUMERIC(16,2) NOT NULL,
    FOREIGN KEY (stock_operation_id) REFERENCES stock_operation(id)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;