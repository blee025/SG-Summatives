Use HotelReservation;

-- 1. Write a query that returns a list of reservations that end in July 2023, including the name of the guest, 
-- the room number(s), and the reservation dates.
Select Concat(G.FirstName, ' ', G.LastName) `Name`, RR.RoomId, R.DateIn, R.DateOut
From Reservations R 
Inner Join Guests G On R.GuestId = G.GuestId
Inner Join ReservedRooms RR On R.ReservationId = RR.ReservationId 
Where R.DateOut between '2023-07-01' and '2023-07-31';
-- 'Bee Lee', '205', '2023-06-28', '2023-07-02'
-- 'Walter Holaway', '204', '2023-07-13', '2023-07-14'
-- 'Wilfred Vise', '401', '2023-07-18', '2023-07-21'
-- 'Bettyann Seery', '303', '2023-07-28', '2023-07-29'

-- 2. Write a query that returns a list of all reservations for rooms with a jacuzzi, displaying the guest's name, 
-- the room number, and the dates of the reservation.
Select Concat(G.FirstName, ' ', G.LastName) `Name`, Ro.RoomId, R.DateIn, R.DateOut
From Reservations R 
Inner Join Guests G On R.GuestId = G.GuestId
Inner Join ReservedRooms RR On R.ReservationId = RR.ReservationId
Inner Join Rooms Ro On RR.RoomId = Ro.RoomId 
Inner Join RoomAmenities Ra On Ro.RoomId = Ra.RoomId 
-- Inner Join Amenities A On Ra.AmenitiesId = A.AmenitiesId
Where Ra.AmenitiesId = '3';
-- 'Karie Yang', '201', '2023-03-06', '2023-03-07'
-- 'Bettyann Seery', '203', '2023-02-05', '2023-02-10'
-- 'Karie Yang', '203', '2023-09-13', '2023-09-15'
-- 'Bee Lee', '205', '2023-06-28', '2023-07-02'
-- 'Wilfred Vise', '207', '2023-04-23', '2023-04-24'
-- 'Walter Holaway', '301', '2023-04-09', '2023-04-13'
-- 'Mack Simmer', '301', '2023-11-22', '2023-11-25'
-- 'Bettyann Seery', '303', '2023-07-28', '2023-07-29'
-- 'Duane Cullison', '305', '2023-02-22', '2023-02-24'
-- 'Bettyann Seery', '305', '2023-08-30', '2023-09-01'
-- 'Bee Lee', '307', '2023-03-17', '2023-03-20'

-- 3. Write a query that returns all the rooms reserved for a specific guest, including the guest's name, 
-- the room(s) reserved, the starting date of the reservation, and how many people were included in the reservation. 
-- (Choose a guest's name from the existing data.)
Select Concat(G.FirstName, ' ', G.LastName) `Name`, RR.RoomId, R.DateIn, R.Adults, R.Kids 
From Guests G
Inner Join Reservations R On G.GuestId = R.GuestId
Inner Join ReservedRooms RR On R.ReservationId = RR.ReservationId 
Inner Join Rooms Ro On RR.RoomId = Ro.RoomId 
Where G.GuestId = '6';
-- 'Aurore Lipton', '302', '2023-03-18', '3', '0'
-- 'Aurore Lipton', '304', '2023-06-17', '3', '0'

-- 4. Write a query that returns a list of rooms, reservation ID, and per-room cost for each reservation. 
-- The results should include all rooms, whether or not there is a reservation associated with the room.
Select Ro.RoomId, R.ReservationId, ((Rt.BasePrice + Sum(A.Price) + If(R.Adults > Rt.StdOccupancy, Rt.Exoccupancy * (R.Adults - Rt.Stdoccupancy), '0'))* DateDiff(R.DateOut, R.DateIn)) Total  
From Rooms Ro 
Left Join ReservedRooms RR On Ro.RoomId = RR.RoomId 
Left Join Reservations R On RR.ReservationId = R.ReservationId 
Inner Join RoomAmenities Ra On Ro.RoomId = Ra.RoomId
Inner Join Amenities A On Ra.AmenitiesId = A.AmenitiesId 
Inner Join RoomTypes Rt On Ro.RoomTypeId = Rt.RoomTypeId
Group By Ro.RoomId, R.ReservationId
Order By R.ReservationId Asc;
-- '306', NULL, NULL
-- '402', NULL, NULL
-- '308', '1', '299.98'
-- '203', '2', '999.95'
-- '305', '3', '349.98'
-- '201', '4', '199.99'
-- '307', '5', '524.97'
-- '302', '6', '924.95'
-- '202', '7', '349.98'
-- '301', '9', '799.96'
-- '207', '10', '174.99'
-- '401', '11', '1199.97'
-- '206', '12', '599.96'
-- '208', '13', '599.96'
-- '304', '14', '184.99'
-- '205', '15', '699.96'
-- '204', '16', '184.99'
-- '401', '17', '1259.97'
-- '303', '18', '199.99'
-- '305', '19', '349.98'
-- '208', '20', '149.99'
-- '203', '21', '399.98'
-- '401', '22', '1199.97'
-- '206', '23', '449.97'
-- '301', '24', '599.97'
-- '302', '25', '699.96'


-- 5. Write a query that returns all the rooms accommodating at least three guests and that are reserved on any date in April 2023.
Select Ro.RoomId
From Reservations R
Inner Join ReservedRooms RR On R.ReservationId = RR.ReservationId
Inner Join Rooms Ro On RR.RoomId = Ro.RoomId
Inner Join RoomTypes Rt On Ro.RoomtypeId = RT.RoomtypeId and RT.MaxOccupancy >= '3'
Where (R.DateIn between '2023-04-01' and '2023-04-30') Or (R.DateOut between '2023-04-01' and '2023-04-30');
 -- '301'


-- 6. Write a query that returns a list of all guest names and the number of reservations per guest, 
-- sorted starting with the guest with the most reservations and then by the guest's last name.
Select Concat(G.FirstName, ' ', G.LastName) `Name`, count(R.ReservationId) ReservationAmount
From Guests G
Inner Join Reservations R On G.GuestId = R.GuestId
Group By `Name`
Order By count(R.ReservationId) Desc, G.LastName;
-- 'Mack Simmer', '4'
-- 'Bettyann Seery', '3'
-- 'Duane Cullison', '2'
-- 'Walter Holaway', '2'
-- 'Bee Lee', '2'
-- 'Aurore Lipton', '2'
-- 'Maritza Tilton', '2'
-- 'Joleen Tison', '2'
-- 'Wilfred Vise', '2'
-- 'Karie Yang', '2'
-- 'Zachery Luechtefeld', '1'


-- 7. Write a query that displays the name, address, and phone number of a guest based on their phone number.
-- (Choose a phone number from the existing data.)
Select Concat(G.FirstName, ' ', G.LastName) `Name`, G.Address, G.Phone
From Guests G
Where G.Phone = '8144852615';
-- 'Zachery Luechtefeld', '7 Poplar Dr.', '8144852615'
