#MedicineStorageExaminer 

Task bord of project: https://trello.com/b/OE3OIbmC/fabrika-final-project 

This project helps to get info about leftovers of aids from the site: " 
liky.od.ua". It takes HTTP request: 
localhost:examiner/api/?location="Ананьївський район"&name="Центральна 
районна лікарня" Returns the info in the json form. 

Project's abilities: 

- download PDF files from the site 
- extract PDF data 
- parse extracted text in formatted data 
- make domain objects based on parsed data 
- serialize data to json format 
- save data to MobgoDB storage 
- provide json response with asked data from MongoDB storage 

URL HTTP Method Operation 

/api/ GET Returns all saved data objects in json format 

/api/?location="Ананьївський район"&name="Центральна районна лікарня" GET 
Returns all saved data objects in json format for "Центральна районна 
лікарня" in "Ананьївський район" 
