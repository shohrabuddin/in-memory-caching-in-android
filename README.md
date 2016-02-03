# in-memory-caching-in-android

## Introduction

Sometimes you might need to load data from in-momery instead of making expensive API call over the network. In-memory caching mechanism not only save user's bandwidth but also significantly reduce the number is hits/second into your server as well as improve the performance of your lovely app. From this tutorial you will learn how to implement in-memory caching feature using Google Guava API.

## Google Guava API
The Guava project contains several of Google's core libraries that we rely on in our Java-based projects: collections, caching, primitives support, concurrency libraries, common annotations, string processing, I/O, and so forth. Each of these tools really do get used every day by Googlers, in production services. 

Caching is one of the libraries of Guava API. In this project will use this library to implement im-memory caching feature.

## The Project
The project is pretty simple. You might be benifited most from in-memory caching feature if you call any API. But to keep it simple I am not going to call any API in this project rather I would retrieve contact name and image of a particular phone number from a real device. The goal is to retrieve contact information (name and image) from the corresponding content provider just once and in the subsequest request I will load the data from in-memory cache. 

### Gradle Setup
You have to add __compile 'com.google.guava:guava:19.0'__ or any latest version in your build.gradle file.

### Implementation

__Activity__
There is only one activity class called MainActivity. This class handles the user input (mobile number) and button click (to show name and image). 
__Model__
ContactModel class is used to model name and image data.

__Utility__
Utility class is the main part of this project. This class validates any given mobile number, retrieves contact data from content provider and performs in-memory caching.

__Adapter__
You can find an adapter class which is used to show contact data in a list view.

__Layouts__
There are two layout files availble; one is main_activity.xml and the other one is layout_list_view.xml

__Note: I put a lot of comments in the project. So you will dam easily understand each line of code.__


You are welcome to fork/clone the pjoject.

Best of luck

