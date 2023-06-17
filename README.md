# upload and download file from aws S3

## Introduction

I have implemented the front end and backend in this project
I have integrated frontend using thymleaf
Frontend is in src/main/resources/templates
## Features

This application contains the APIs for the following features:

- File upload: Every User can upload file.
- Download File: Every User can download any file.

## Features I wanted to implement but couldn't because of time constraints

- User based upload and download: I wanted to make the feature in which the user can download only those files which are uploaded by him/her.
- Delete File: I wanted to implement deletion of the file.
- Used Interfaces to make the code extendible.
- Used multiple layers so that repository does not get exposed to the user to make more secure.

#### This application is hosted on : http://ec2-43-205-118-90.ap-south-1.compute.amazonaws.com:8080/index

#### Run locally

To run this application locally, you will need an AWS account.

1. Clone this repository on your device.
2. Replace the following variables in src/main/resources/application.properties:
   - aws.access.key.id = access key of your aws account
   - aws.secret.access.key = secret access key for your aws account.
   - aws.s3.region=ap-south-1 = region.
3. Change the name of the bucket with the name of you S3 bucket in src/main/java/configuration/BucketName
4. Install the dependencies using the command `mvn clean install`(You will require maven and java installed on your device to run this application)
5. Once the dependencies are installed, run using `java -jar target/upload.download-0.0.1-SNAPSHOT.jar
