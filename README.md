# se-card
Documentation:


POST /login
    Request params: 
      username: Username to login 
      pword: Password 

    Response:
      If successful: JSON: {"ok": true, "username": [username], "name", [name], "lastname": [lastname], "email": [email]}
      else:JSON: {"ok": false, "err":[Error message]}
 
    Comments: 
      No comments
     
POST /register
    Request params:
      username: Username to resiter
      pword: Password 
      name: Name to register
      lastname: Lastname to register
      email: Email to register
      
    
    
