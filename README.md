# Masrapt App

The main purpose of this app is to provide information about buses, their routes, their status, available seats and real-time location.

> The application has its own interaction API.
> To run it, the API must be in **run state**.

## Installation / How to run

### - First
You have cloned this repos.
Then you have to open it with Android Studio.

##### - Second
Make sure that the API is running.
Check the instruction and the API on [Masrapt API](https://github.com/robertocarlosmedina/masrapt-api)
If you want to run the APP on your mobile, you need to run the API on an [Apache Server](https://ubuntu.com/tutorials/install-and-configure-apache#1-overview)  or using a [XAMPP Server](https://www.apachefriends.org/es/index.html)

##### - Third
You have to go to the **string.xml** values and change the **api_base_url** value to the Base URL in witch the API is running.
For example:
- On your Android Phone
```sh
http://192.168.0.16:3001/
```
- On the Android simulator
```sh
http://10.0.2.2:3001/
```

##### - Lastly
Test the APP interface on your phone or emulator.

## License

This repo use MIT License.

**"The code needs to go through different approaches before it is as effective as possible"**
