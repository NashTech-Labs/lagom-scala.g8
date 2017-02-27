# lagom-scala

**# Prerequisites**
For running this sample application, you need to create application on twitter (https://apps.twitter.com) for generating security keys. After creating a sample app, you need to add security key on "producer-impl" module application.conf file.

```
twitter {
          consumerKey="XXXXXX"
          consumerSecret="XXXX-XXXXXX"
          accessToken="XXXXXXX"
          accessTokenSecret="XXXXXXXXXXXX"
        }
```
**# Start Application**
In this application, we are using Akka Scheduler for fetching tweets from twitter every 2 min. For start this scheduler you need to hit scheduler service :

```
GET /scheduler HTTP/1.1
Host: localhost:9000
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 41ef7f7a-86e5-7953-603b-4a98a5f4af5f
```
For http request, you can use any tool like postman or curl. After the scheduler start, the flow of the application is like : Fetch tweets from twitter and send it to the producer tweet service. The tweet service, persist the tweets event to cassandra and also put messages intotweet topic.
**consumer** module read tweets from Apache Kafka topic and save events to cassandra and also save tweet data into Cassandra table.
For more queries, drop mail to **harmeet@knoldus.com**