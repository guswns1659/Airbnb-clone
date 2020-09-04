-- SET GLOBAL local_infile = 1;

LOAD DATA LOCAL INFILE '/home/jack/Downloads/airbnbProject/airbnbPicture.csv'
    INTO TABLE airbnb.picture FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n'
    (@var1, @var2, @var3)
    SET
    picture_id = @var1,
    picture_url = @var2,
    accommodation_id = @var3;