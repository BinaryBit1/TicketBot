# TicketBot

[![Steamy](https://github.com/WesternPine/TicketBot/blob/master/ticketbot.png?raw=true)](https://discordapp.com/api/oauth2/authorize?client_id=498422164077150218&permissions=268463120&scope=bot)

^ [Click To Add Me!](https://discordapp.com/api/oauth2/authorize?client_id=498422164077150218&permissions=268463120&scope=bot)

## Introduction

TicketBot was created as an effort to implement a support system into any discord environment, whether it be a community gaming server, or a software development space. If you plan on hosting your own bot, MySQL is a requirement as the TicketBot was made to run without using any config files if possible. If you have the bot added to your server already, take a look at [Usage](https://github.com/WesternPine/TicketBot#usage). Otherwise, let's get started!

## Downloading The Jar (Requires A Java IDE, Java 8 JDK, and Git):

  - Clone the TicketBot Repository, and add to your IDE.
  - Run the project as a `Maven Build` with goals of `clean install`
  - Production Jar-> `Project-Folder/target/TicketBot-X.jar`
  
## Pre-Set-Up Information:

To set up and run the jar, there are a few different options depending on how you run it. All options use the same configuration keys to identify values in any of the 3 launch options. If any keys were not set-up correctly, the program will default to the next lowest tier until using a configuration file variable that would be automatically generated. In English: We got your back. <3

### Configuration Keys

The following are configuration keys to be used when setting up the bot.

| Key | Description |
|-----|-------------|
| BOT_TOKEN | Token for account to be used by the bot. |
| COMMAND_PREFIX | The command prefix to be listening for. |
| SQL_IP | The Ip of the MySQL database. |
| SQL_PORT | The port to use for the MySQL database. |
| SQL_DATABASE | The SQL Database to use. |
| SQL_USERNAME | The Username of the account to be used. |
| SQL_PASSWORD | The password for the user account to be used. |

### MySQL

TicketBot requires a SQL database to create tables (one for each server), to delete values from those tables, and to insert values into them as well. As long as the bot has those permissions, everything else is automated from there.

## Setup (almost there...)

  - Failover/Fallback Configuration Hierarchy: 
  
```
Startup Arguments > System Environmental Variables > Configuration File (Automatically Generated, Last Resort)
```

  - Starup Arguments:
  
```
java -jar TicketBot-X.jar -[Configuration Key] [Value] -[Configuration Key 2] [Value] (etc...)
```

  - Environmental Variables:

    - This is mostly used for services such as [Heroku](https://heroku.com) where you can set the variables manually. 

  - Configuration File:
  
```
{[Configuration Key]: [Value],[Configuration Key]: [Value], (etc...)}
```

## Starting The Bot (finally!)

Start your bot in any of the 3 ways listed above, with the proper configuration information set up. (Please have MySQL set up before starting the bot... We programmers don't code magic! :P) Add the bot account used, to your server if you havn't already, and type the help command to get started!


## Usage

Whether you made your own bot, or want to use the [pre-existing one (Click Here)](https://discordapp.com/api/oauth2/authorize?client_id=498422164077150218&permissions=268463120&scope=bot), Using the bot is the same.

___'Support Specialist' Role:___

Administrators and memebers with the "Support Specialist" role:

  - May modify the blacklisted users.
  - May create unlimited tickets.
  - Have access to all tickets.
  - May modify ticket members.
  - May close any tickets.

___Commands:___

Blacklist Commands

  - !blacklist add ExampleUser#0000
  - !blacklist remove ExampleUser#0000
  
Support Type Commands

  - !enable (Support Type)
  - !disable (Support Type)

___Support Types___

  - ban
  - billing
  - bug
  - question
  - request
  - suggest
  - support
  - ticket

Please note that by default, all support types are disabled for your server. To enable them, use the enable command followed by any of the listed support types. The response by the bot when using the help command is also automatically changed, this way when a user uses the help command, it shows which support type commands they may use. Ticket owners also have exclusive permission to add and remove users, and close tickets. Only those that are added to the tickets have the ability to leave tickets without closing them. And that's everything to know about TicketBot! If you have any other questions, comments, or concerns, feel free to contact me here on github or using my Discord information located in my profile. Thank you!

License
----

[MIT](https://choosealicense.com/)