
### Database Schema
#### Address Table
```sql
CREATE TABLE Address(
    addressId int primary key auto_increment,
    address varchar(50),
    pincode int not null,
    landmark varchar(30)
) auto_increment = 200;
```

#### Restaurant Table
```sql
CREATE TABLE Restaurant(
    restaurantId int primary key auto_increment,
    restaurantName varchar(30) not null,
    description varchar(50),
    addressId int, foreign key (addressId) references Address(addressId),
    rating double,
    ownerName varchar(20),
    managerId varchar(20), foreign key (managerId) references User(userid),
    available ENUM('yes','no') default 'yes'
) auto_increment = 500;
```

#### FoodItem Table
```sql
CREATE TABLE FoodItem(
    foodId int primary key auto_increment,
    foodName varchar(30) not null,
    description varchar(50),
    rating double,
    price int,
    restaurantId int, foreign key (restaurantId) references Restaurant(restaurantId) ON DELETE CASCADE,
    available ENUM('yes','no') default 'yes'
) auto_increment = 600;
```

#### User Table
```sql
CREATE TABLE User(
    userId varchar(20) primary key,
    name varchar(20) not null,
    password varchar(20) not null,
    addressId int, foreign key (addressId) references Address(addressId) ON DELETE SET NULL,
    email varchar(30) unique not null,
    phoneNumber varchar(20) not null,
    role ENUM('admin','manager','customer') not null
);
```

#### Cart Table
```sql
CREATE TABLE Cart(
    cartId int primary key auto_increment,
    totalPrice int
) auto_increment = 700;
```

#### CartFoodItems Table
```sql
CREATE TABLE CartFoodItems(
    cartId int, foreign key (cartId) references Cart(cartId),
    foodId int, foreign key (foodId) references FoodItem(foodId),
    quantity int,
    primary key(cartId, foodId)
);
```

#### Payment Table
```sql
CREATE TABLE Payment(
    paymentId int primary key auto_increment,
    paymentStatus varchar(20),
    paymentMethod varchar(20)
) auto_increment = 900;
```

#### User_Cart Table
```sql
CREATE TABLE User_Cart(
    userId varchar(20), foreign key (userId) references User(userId),
    cartId int, foreign key (cartId) references Cart(cartId),
    primary key(userId, cartId)
);
```

#### CustomerOrder Table
```sql
CREATE TABLE CustomerOrder(
    orderId int primary key auto_increment,
    orderDate timestamp default current_timestamp,
    userId varchar(20), foreign key (userId) references User(userId) ON DELETE SET NULL,
    status varchar(20),
    paymentId int, foreign key (paymentId) references Payment(paymentId) ON DELETE SET NULL
) auto_increment = 800;
```

