drop database if exists HotelReservation;

create database HotelReservation;

use HotelReservation;

Create Table Guests (
	GuestId			int			primary key auto_increment,
    FirstName		varchar(75) Not Null,
    LastName		varchar(75) Not Null,
    Address			varchar(75)	Not Null, 
    City 			varchar(75)	Not Null,
    Phone 			BigInt		Not Null 
);

Create Table Reservations (
-- 	ColumnName		Type		Rules	
	ReservationId	int 		primary key auto_increment,
    GuestId			int			Not Null,
    Adults       	int 		Not Null, 
    Kids			int 		Not Null,
    DateIn			date		Not Null,
    DateOut			date		Not Null,

    foreign key fk_Reservations_Guests (GuestId)
	References Guests(GuestId)
    
);


Create Table RoomTypes (
	RoomTypeId		int 		Primary key Not Null,
    Name 			varchar(30) Not Null,
    StdOccupancy 	int			Not Null,
    MaxOccupancy	int			Not Null,
    BasePrice		Numeric(5,2) Not Null,
    ExOccupancy  	Numeric(2,0) Not Null
);

Create Table Rooms (
	RoomId			int			Primary key Not Null,
    RoomTypeId		int			Not Null, 
    AdaAccessible   bit			Not Null default 0,			
    
    foreign key fk_Rooms_RoomTypes (RoomTypeId)
	References RoomTypes(RoomTypeId)
);

Create Table ReservedRooms (
	ReservationId 	int			Not Null,
    RoomId			int			Not Null,
    
    primary key (ReservationId, RoomId),
    foreign key fk_ReservedRooms_ReservationId (ReservationId)
	References Reservations(ReservationId),
    
    foreign key fk_ReservedRooms_RoomId (RoomId)
	References Rooms(RoomId)

);


Create Table Amenities (
	AmenitiesId		int			Primary key Not Null,
    Name			varchar(100) Not Null,
    Price			Numeric(2,0) Null
);

Create Table RoomAmenities (
	RoomId 				int			Not Null,
    AmenitiesId			int			Not Null,
    
    primary key (RoomId, AmenitiesId),
    foreign key fk_RoomAmenities_RoomId (RoomId)
	References Rooms(RoomId),
    
    foreign key fk_RoomAmenities_AmenitiesId (AmenitiesId)
	References Amenities(AmenitiesId)
);
