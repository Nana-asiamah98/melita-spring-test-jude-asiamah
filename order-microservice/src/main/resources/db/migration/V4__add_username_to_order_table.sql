ALTER TABLE tbl_order ADD COLUMN IF NOT EXISTS username varchar(100) default null;
ALTER TABLE tbl_order ADD COLUMN IF NOT EXISTS comment text default null;


