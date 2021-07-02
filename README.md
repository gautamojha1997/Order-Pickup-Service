# Ecommerce Order Picking Service
The Project follows microservice architecture for developing a full Ecommerce Service, I have implemented order picking 
microservice.

## DB Design / ER Diagram 

![DB Schema](db%20Schema.png)


## Flow of my Project 

I have assumed a pickup as single or batch based on numbers of orders, all pickups with single orders are 
Single Pickups and else is Batch.

The left picture is for Batch Pickup and the right is for Single Pickup:

![Flow](flow.JPG)

1. One pickup(P) can have many orders(O).
2. One Order(O) can have many items (I).
3. Multiple Items can be a part of single Tote(T).
4. Multiple Totes(T) can be a part of single Cart(C).
5. Multiple Carts(C) can be a part of a Pickup.


## API Endpoints Description 
- The description of the endpoints can be referred by running the project and going to following link :
    
```http://localhost:8080/api/swagger-ui/```

