DROP DATABASE IF EXISTS SuperheroSightingsTestDB;

CREATE DATABASE SuperheroSightingsTestDB;

USE SuperheroSightingsTestDB;


Create table Powers (
	Id					int				Primary Key auto_increment,				
    `Name`				varchar(30)		not null
    
);

Create table Supes (
	Id					int				Primary Key auto_increment,				
    `Name`				varchar(30)		not null,
    `Description`		varchar(150)	not null,
    `PowerId`			int				not null,
    
    foreign key fk_Super_Power (PowerId)
	references Powers(Id)
  
);

Create table Organizations (
	Id					int				Primary Key auto_increment,				
    `Name`				varchar(30)		not null,
    `Description`		varchar(150)	not null,
    Address				varchar(60)		not null
    
);

Create table SuperOrganizations (
	`OrganizationId`	int,
    `SupeId`			int,
    
    Primary key (OrganizationId, SupeId),
    
    foreign key fk_SuperOrganizations_Organization (OrganizationId)
    references Organizations(Id),
    
    foreign key fk_SuperOrganizations_Supe (SupeId)
    references Supes(Id)

);

Create table Locations (
	Id					int				Primary Key auto_increment,				
    `Name`				varchar(30)		not null,
    Address				varchar(60)		not null,			
    Latitude			Decimal(9,6)	not null,
    Longitude			Decimal(9,6)	not null
    
);

Create table Sightings (
	Id					int				Primary Key auto_increment,			
	`Date`				date			not null,
    `LocationId`		int				not null,
    `SupeId`			int 			not null,
    
    foreign key fk_Sighting_Location (LocationId)
    references Locations(Id),
    
    foreign key fk_Sightings_Supe (SupeId)
    references Supes(Id)
);

Insert into Powers (Id, `Name`)
Values 
('1','Fire'),
('2','Water'),
('3','Earth'),
('4','Air'),
('5','Magic');

Insert into Supes (Id, `Name`, `Description`, PowerId)
Values
('1','Phantom','Super cool','5'),
('2','Amethyst','Bad villian','4'),
('3','Cybernetic','Tech dude','2'),
('4','Mistoad','Literal Toad','3'),
('5','Transfreezer','Really cold','2'),
('6','Enchanter','Freaky magic','5'),
('7','Dynafire','So hot','1'),
('8','Luminous','Very bright','1'),
('9','Spectral','Unknown','5'),
('10','Obsidian','Hard material','3');

Insert into Organizations (Id, `Name`, `Description`, Address)
Values
('1','Outcasts','Odd ones','377 Border Road'),
('2','Venomous ','Nasty villians','12 MeadowBrook Street'),
('3','Angels','Top heroes','2224 Harvard Court'),
('4','Mutants','Weird animals','8 Cardinal Lane');

Insert into SuperOrganizations (`OrganizationId`, SupeId)
Values 
('1','2'),
('1','4'),
('1','5'),
('1','9'),
('2','2'),
('2','7'),
('2','10'),
('3','1'),
('3','3'),
('3','6'),
('3','8'),
('3','9'),
('4','4');

Insert into Locations (Id, `Name`, Address, Latitude, Longitude)
Values
('1','Local Lamp Post','1 Clark Ave.','-26.640281','83.469807'),
('2','Clear Blue Pond','2 Creek Street','-8.386259','-70.48434'),
('3','darkest alley','3 Monroe Road','23.97048','110.648223'),
('4','Sally Statue','4 Selby Lane','13.487871','58.255566'),
('5','Crashed Stoplight','5 Overlook Drive','-40.381718','-11.677544'),
('6','Blue House','6 Coffee Lane','4.174554','-127.41775'),
('7','Light Laboratory','7 Woodside Street','55.792117','151.477972'),
('8','Zoo','8 Charles Ave.','13.978816','53.981198'),
('9','Joes Store','9 Pine Street','89.999997','-129.662608'),
('10','James Hospital','10 Elmwood Dr.','81.338119','163.794405');

Insert into Sightings (Id, `Date`, LocationId, SupeId)
Values 
('1','2019-01-04','3','2'),
('2','2019-01-26','7','4'),
('3','2019-02-14','3','9'),
('4','2019-03-18','9','4'),
('5','2019-03-29','5','5'),
('6','2019-05-09','1','1'),
('7','2019-06-13','4','3'),
('8','2019-06-21','6','6'),
('9','2019-07-18','8','7'),
('10','2019-08-12','8','3');
