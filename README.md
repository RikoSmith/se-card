# se-card

## Demo: ##

  http://138.68.108.162:8080/cg-service/api/v1/

## Documentation: ##

### POST /login ###
  Description:
    
    Kinda signs in user to the system

  Request params:
  
    1. username: Username to login 
    2. pword: Password 

  Response:

    - Success: JSON: {"ok": true, "username": [username], "name", [name], "lastname": [lastname], "email": [email]}
    - Fail:JSON: {"ok": false, "err":[Error message]}

  Comments: 

    - No comments
     
### POST /register ###
  Description:
  
    Registers new user to the system

  Request params:
    
    1. username: Username to resiter
    2. pword: Password 
    3. name: Name to register
    4. lastname: Lastname to register
    5. email: Email to register
  
  Response:
  
    - Success: JSON: {"ok": true, "newuser": [Username of registered user]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
   
  Comments:
  
    - Before using this resource one should check existense of a user with the same email and username to avoid 
      duplicate entries (though database level won't allow it). There are two methods described below that one
      should use to implement such behavior.

### GET /searchEmail ###

  Description: 
  
    Searches for email in the system
    
  Request params:
    
    1. email: Email than will be searched
    
  Response:
  
    - Success: JSON: {"ok": true, "email":[Email that searhced]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - It is a helper function that should be used to verify existence of user with the same email during 
      filling sign up form (using ajax).
      
 
### GET /searchUsername ###

  Description: 
  
    Searches for username in the system
    
  Request params:
    
    1. uname: Username that will be searched
    
  Response:
  
    - Success: JSON: {"ok": true, "username":[Username that searhced]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - It is a helper function that should be used to verify existence of user with the same username during 
      filling sign up form (using ajax).
      
      
### GET /getActiveUsers ###

  Description: 
  
    Gets all logged users in the system
    
  Request params:
    
    No params
    
  Response:
  
    - Success: JSON: {"ok": true, "users":[Array of usernames]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - Everytime user logges in his name is added to the list of activeUsers
    
    
    
### GET /logout ###

  Description: 
  
    Loges out the user
    
  Request params:
    
    No params
    
  Response:
  
    - Success: JSON: {"ok": true, "users":[Success message]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - Invalidates session of a user
    
    
    
### GET /getCard ###

  Description: 
  
    Returns the card JSON object
    
  Request params:
    
    1. id: id of the card
    
  Response:
  
    - Success: JSON: {"ok": true, "card": [Card instance]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - Retreives card instance from database
    
    
### GET /getAllCards ###

  Description: 
  
    Returns all the cards
    
  Request params:
    
    No param
    
  Response:
  
    - Success: JSON: {"ok": true, "data": [Array of cards]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - Retreives all card instances from database
    
    
### GET /newLobby ###

  Description: 
  
    Creates new lobby with capacity of two players and returns it as JSON. Lobbies are the rooms with players. They are persistently stored and modified. 
    
  Request params:
    
    No param
    
  Response:
  
    - Success: JSON: {
        "_id": {
            "$oid": [Mongo assigned ID]
        },
        "p1": [Player One (creator of Lobby)],
        "p2": [Second Player (initially low)],
        "age": [Increments every turn of players],
        "isFull": [True if both players are in],
        "expectedMove": [Current turn],
        "lastdata": [Exchange data - state of the decks],
        "code": [Unique code of Lobby]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - Requires player to be logged in.
      
      
### GET /checkLobby ###

  Description: 
  
    Returns lobby object by key. It is used to check if the second player joined or the turn of a player.
    
  Request params:
    
    1. key - key of the Lobby ('code' field)
    
  Response:
  
    - Success: JSON: returns Lobby object
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - Requires player to be logged in.
      
      
      
### POST /turn ###

  Description: 
  
    Makes turn on a game.
    
  Request params:
    
    1. key - key of the Lobby ('code' field)
    2. data - new state information of the board
    
  Response:
  
    - Success: JSON: {"ok": true, "message": [Error message]}
    - Fail: JSON: {"ok": false, "err": [Error message]}
    
  Comments:
  
    - Requires player to be logged in and be in the corresponding lobby and be the turn of the player.
      


