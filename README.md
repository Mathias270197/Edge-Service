Wij hebben gekozen om een mobile app te maken over lego. De bedoeling is dat je een stap van een legofiguur kan scannen om zo te zien hoeveel sterren je het figuurtje geeft. Daarnaast kan je ook een geschreven review achterlaten over het figuurtje.

https://github.com/mathias270197/steps-service
https://github.com/mathias270197/figurereview-service


![image](https://user-images.githubusercontent.com/58940744/200191287-2ef34358-0086-4185-a829-f5261dabc5b7.png)

Postman:

https://edge-service-mathias270197.cloud.okteto.net/numberOfStepReviewsByFigure

![image](https://user-images.githubusercontent.com/58940744/201534190-0bd5cb51-2ea5-497e-b441-ad57d8a04536.png)

 

https://edge-service-mathias270197.cloud.okteto.net/reviewAndStepsOfFigure/Duck
 
 ![image](https://user-images.githubusercontent.com/58940744/201534194-f76b2e28-fd44-410e-821d-e9a5454328d7.png)


https://edge-service-mathias270197.cloud.okteto.net/figureReview
{"id":null,"figureName":"Chicken","date":1666947596193,"textReview":"Eenvoudige stappen","stars":3,"user":"Mathias"}


![image](https://user-images.githubusercontent.com/58940744/201534197-92f0c445-f48f-4bb7-b94e-e8be7233d796.png)

 

https://edge-service-mathias270197.cloud.okteto.net/figureReview
{"id":null,"figureName":"Chicken","date":1666947596193,"textReview":"Eenvoudige stappen, goed omschreven","stars":4,"user":"Mathias"}

![image](https://user-images.githubusercontent.com/58940744/201534205-339d4924-c6a6-434c-95bd-b9617bb719a2.png)

 

https://edge-service-mathias270197.cloud.okteto.net/figureReviewByNameAndUser/Chicken/Mathias
 
 ![image](https://user-images.githubusercontent.com/58940744/201534210-fb15e0af-2f60-423c-8eb7-5b929b482d4f.png)

 

https://edge-service-mathias270197.cloud.okteto.net//averageStarRatingOfFigure/House
 
![image](https://user-images.githubusercontent.com/58940744/201534220-5d90c149-e592-49ec-9165-7226f6d1b28e.png)



https://edge-service-mathias270197.cloud.okteto.net/figureReviewByNameAndUser/House/Stijn
 
![image](https://user-images.githubusercontent.com/58940744/201534222-cca8ff97-5b07-4624-97a4-784eb6677c63.png)



Swagger UI:


![image](https://user-images.githubusercontent.com/58940744/200191540-96894142-5a01-4603-8615-82a4f208c1c5.png)

![image](https://user-images.githubusercontent.com/58940744/200191520-026cef39-0483-4955-8ba6-1fb3a864239d.png)

![image](https://user-images.githubusercontent.com/58940744/200191560-43f1bb8c-c9cf-4a1f-a759-c3eb930573ed.png)

![image](https://user-images.githubusercontent.com/58940744/200191578-fbebabab-9b60-4796-82aa-53ac01123eef.png)

![image](https://user-images.githubusercontent.com/58940744/201533883-f7f76432-3996-4b5a-92e9-23a3a32405e3.png)

![image](https://user-images.githubusercontent.com/58940744/201533894-fc23a6b5-eb56-4acf-b539-e3bd043edadb.png)

![image](https://user-images.githubusercontent.com/58940744/201533951-71b60639-bc71-436d-bbf4-d5cf539a6edc.png)

![image](https://user-images.githubusercontent.com/58940744/201533957-59d7e2a3-a444-432b-92ed-3fa073bcf1bf.png)

![image](https://user-images.githubusercontent.com/58940744/201533981-c614841a-9b24-4922-a46d-45216c9d69fb.png)

![image](https://user-images.githubusercontent.com/58940744/201533988-224154f3-8e52-48b9-9267-1ab61e736354.png)

test coverage:

steps-service:
![image](https://user-images.githubusercontent.com/58940744/202681621-a0c36919-5a01-44a9-84ae-f550788e47f1.png)

figurereview-service:
![image](https://user-images.githubusercontent.com/58940744/202681771-54d6e4bd-109d-4fa4-8e7d-d3fb247bae7d.png)

edge-service:
![image](https://user-images.githubusercontent.com/58940744/202681905-a289203e-039c-44eb-950a-579c2c2190a9.png)


