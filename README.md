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
      
