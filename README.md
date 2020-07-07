# Running the application

## Starting the database and application

This application requires a MongoDB running, the project contains a docker-compose that allow the 
user to start such database.

When the application is started, by default, it will load the data from the assets json (proposed
in the exercise).

You can do the following to start the application:
```
cd docker
docker-compose up -d mongodb-doodle-test
```

Once the database is started you can start the application using the build (gradle):
```
# Windows
gradlew.bat bootRun

# Linux/MacOs
./gradlew bootRun
```

If you don't want the data to be automatically loaded by the application just add the parameters:
```
./gradlew bootRun -Pargs=--tst.locj.doodle.loadDataFromFile=true
```

Notice that both the application as well the database open ports on the host machine. The application
open the port `8080`.

## Calling the application

You can call the 3 methods with those URLs:
1. List all polls created by a user: `http://localhost:8080/initiator/polls?email=`
1. Search polls by its title: `http://localhost:8080/polls?title=`
1. List all polls created after a certain date: `http://localhost:8080/polls?since=`

Here are example of calls that return data:
* `http://localhost:8080/initiator/polls?email=mh%2Bsample%40doodle.com`
* `http://localhost:8080/polls?title=Marvel`
* `http://localhost:8080/polls?since=2017-01-01`

## Running the application through Docker
If you desire to run the application using Docker instead of `gradlew`, that is also possible.

First you need to build the application:
```
./gradlew build
```

After that you just need to run the application through docker compose, it will use the `Dockerfile`
to create the application:
```
cd docker

docker-compose up -d app
```

# Database choice and code structure

The chosen database for this implementation was MongoDB, the reason was basically because that is a
simple database to init with docker as well doesn't require any special configuration to start storing
data on the database. It was all to reduce the time spent on this exercise, although a Document database
would fit this kind of application, but I would consider other options.

The code was also simplified, controller, repository and the data objects. In a real life at least
another level (kind of the business level) would exist.

The controller is also simple and doesn't have the robustness to be a real WebAPI, but represent a idea of
how that is done.

The choice to use WebFlux (https://docs.spring.io/spring-framework/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/web-reactive.html) 
and to create a reactive interface isn't due the need of this project but what I believe is a good practice for WebAPIs, it prepare
the code to handle a possible heave load application. It isn't the only piece of the puzzle, but it
is something that is easy to start with, but hard to adapt after.  
 
# Data object

I have the habit of calling them Value Objects (add an explanation here), so the data objects are 
inside of the vo folder.  
 
Enumeration created based on how it looks like inside of the data structure, values that are a finit
set where definied as enumerations as well as the one that had only one value (state) but it would
potentially have more than one, also if I assume that the data structure was extracted from an
existing system which was developed in java enumeration/constants have the convention of being defined as
upper case text.
  
Files defined as enumerations:
* type
* preferencesType
* state

You will see that poll options, state and type have poll in the name and other data objects not. That is because
those terms are quite common and it is easy to find conflict on those, so better to prefix to which
object they relate to.

**Why optionsHash isn't extracted automatically?**
To be sincere because I am not sure the point of the attribute, so I just kept as another attribute.

**Why invitees is a list of string?**
From the data sample provided it isn't possible to extrapolate the structure, so I kept it simple and defined as a string. I could have defined that as an object that isn't sound when you want to deserialize the string into an object (it would make the code more complex without a point).

**Why Poll class has two sub classes?**
Here is a question to be the easiest to handle a different kind of options per type. There are a couple of ways
to do so, but that is the simplest one, without too much of a code.