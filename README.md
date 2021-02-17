CEEnter API Caller
---
The API Caller is an example GUI which helps generating the proper API syntax.
Ultimately it may also execute the API call, but not at this stage yet.
The metadata for API Caller is under the api-caller folder.
The first file is the menu-map.json where the interactive menu options are defined.

## Build
Create a build using the command gradle
Because this is a prototype, the test code is not currently maintained. 
Therefore, it is necessary to call build with skipped tests.
```
./gradlew build -x test
```
To prepare for distribution
```
./gradlew distZip
```

## Run - locally
For experiments on the local computer, the program can be run as follows
For experiments on the local computer, the program can be run as follows:
1. run ```git clone << address of this git repo >>```
2. go to folder ```cd << project folder >>``` eg ```cd CEEnterCaller```
3. build see above
4. ```unzip build/distributions/ceeredhat-0.0.1.zip```
5. go to folder wuth binary eg ```cd ceeredhat-0.0.1/bin```
6. and run ```./ceeredhat```

## Docker image
To create a docker image, run the following in the project folder
```
docker build -f docker/Dockerfile.jvm -t chytrik/ceeredhat .
```
To run a local image using the docker engine eg
```
docker run -i --rm -p 8080:8080 chytrik/ceeredhat
```

## ODO
I use OpenShift Do for quick deployment.
It is really easy.
I log in to OpenShift (oc login ...) and create an application and deploy user code on an OpenShift, e.g.
```
odo create java ceeredhat --binary build/libs/ceeredhat-0.0.3-all.jar --s2i
odo url create ceeredhat --port 8080
odo push
```

Once the application is created, I make changes using only using
```
odo push
```

I'm using environment variables.
For Ktor I'm setting KTOR_ENV for production
```
odo config set --env KTOR_ENV=prod
```

If necessary, everything can be deleted with
```
odo delete --all
```

## Postgres Database
I'm using local docker Postgres database, and also database Postgres in OpenShift
### Creating a Remote Database Connection
In order to access the database from a database administration tool running on your own local machine, 
it will be necessary to expose the database service outside of the OpenShift cluster.
```
oc port-forward <pod-name> <local-port>:<remote:port>
ex.
oc port-forward postgresql-1-9npdc 15432:5432
```
### Schema
```
create table ceecaller.settings
(
parameter  varchar(50),
value varchar(50)
);

alter table ceecaller.settings
owner to postgres;

INSERT INTO ceecaller.settings (parameter, value) VALUES ('toweradm', 'admin');
INSERT INTO ceecaller.settings (parameter, value) VALUES ('towerpass', 'passWORD');
```

## Apache FreeMarker is used to generate HTML

URL
https://freemarker.apache.org/
