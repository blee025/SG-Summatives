DROP DATABASE IF EXISTS GuessTheNumberDB;

CREATE DATABASE GuessTheNumberDB;

USE GuessTheNumberDB;

Create table Games (
	Id					int 		Primary key auto_increment,
    TargetNumber		int 		Not Null,
    GameFinished		boolean 	Not Null default False
    
);

Create table Rounds (
	Id 					int 		Primary key auto_increment, 
    GameId				int 		Not Null,
    GuessNumber			int			Not Null, 
    PartialMatchCount	int			Not Null,
    ExactMatchCount 	int			Not Null,
    `TimeStamp`			varchar(30) Not Null,
    
    foreign key fk_Game_Rounds (GameId)
	References Games(Id)
    

);