# STEM Swiss System Tournament Helper

## Planned Support

There will be two components:

 * Library Supporting Android 2.3+
 * UI Supporting Android 4.0+

The idea here is that in the future, there will be an Android 2.3 interface
implemented if there is desire for one.

## Features

 * Automatically suggesting the number of rounds based on the number of players
   using the function ceil(ln(n_players) / ln(2)).
 * Adding and Deleting Users, supported by an SQLITE Database
 * A configurable Round timer
 * Automatic Scoring
 * Tie breakers using Buchholz Scoring
 * Utilization of International Wargames Federation scoring
    * 3 pts for a win
    * 1 pt for a draw
    * No player can play another player more than once
 * Tabbed Rounds interface, backed by a database
 * Auto pairing
 * Byes

## Models

 * User
    * Name
    * Extra information
 * Game
    * Date started
    * Number of rounds
    * Game Title
 * Pairing
    * FK to Game
    * FK to User 1
    * FK to User 2
    * User 1 Wins
    * User 2 Wins
    * Draws
    * Round Number
    * User 1 Dropped
    * User 2 Dropped

## Views

 * Game List
 * Game Creation
 * Game Editing
 * User List
 * User Creation
 * User Editing
 * Rounds Tabular Interface
 * Pair List
 * Pair Creation
 * Pair Editing
 * Standings
 * Round Timer
 * Settings

## Notifications

 * Round timer running
 * Round timer end
 * Click to open round timer

## Settings

 * Auto Pairing On/Off
 * Round time length

