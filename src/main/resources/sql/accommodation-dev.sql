-- SET GLOBAL local_infile = 1;

LOAD DATA LOCAL INFILE '/home/ubuntu/Airbnb-clone/src/main/resources/airbnbAccommodation.csv'
    INTO TABLE airbnb.accommodation FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n'
    (@var1, @var2, @var3, @var4, @var5, @var6, @var7, @var8, @var9, @var10, @var11, @var12)
    SET
    accommodation_id = @var1,
    accommodation_name = @var2,
    description = @var3,
    location = @var4,
    street = @var5,
    latitude = @var6,
    longitude = @var7,
    available_guest_count = @var8,
    current_price = @var9,
    previous_price = @var10,
    discount_price = @var11,
    hotel_rating = @var12;

