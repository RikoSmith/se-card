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
      

