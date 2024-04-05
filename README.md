# Cat Attack!
Cat Attack is a 1v1, two player fighting game in which players duel in a 2D environment. 

Written completely in Java, **Cat Attack!** is a game still under development. Build as apart of the AP Computer Science Game Collaboration Project, this game was an original idea proposed by group members highly inspired by Street Fighter. 
As it is still in the works, the game may be rough around the edges, with any branch other than main not guaranteed to be functional. 

In addition, [Working Function](https://github.com/EmperorMurfy/Cat_Attack/tree/main?tab=readme-ov-file#-working-features) + [Future Proposed Functions](https://github.com/EmperorMurfy/Cat_Attack/tree/main?tab=readme-ov-file#-working-features) may be out of date, please check the latest release for more up to date info. 

Note: Main is the main release repository, should be the most stable version - suggested to use the files from release for most stable. 
Please check the latest release for work in progress/buggy functions

## ⚙️ Technical Info
Developed & Tested on Macbook M1 Air 
Written on Eclipse IDE using Java JRE 1.8

Please run GraphicMain.java in the src to start the program

**Quick Guide**
1) Download latest release from github, unzip the zip file, open the unzipped folder

2) Create a java project in Eclipse, must be JRE 1.8

3) Drag the downloaded src + bin folders into your project

4) If prompted, overwrite any previous src + bin folders in the newly created java project

5) Open src/lib, right click on the jl1.0.1.jar

6) Select build path -> Add to build path 

## 🎮 Controls 
**Player One - Katze** 
* A - move left
* D - move right 
* W - jump
* S - attack
* Q - shield 
* 1, 2, 3: different stats

**Player Two - Skin Walker**
* ⬅️ - move left
* ➡️ - move right
* ⬆️ - jump
* ⬇️ - attack
* **SHIFT** - shield
* 8, 9, 0: different stats

**Main Menu**
* ESC - will bring up controls, similar to how control button behaves in the pause game menu?
* Start button - starts the game

**In Game**
* ESC - pauses the game, can also be used to unpause if paused - brings up pause game menu
     - restart button: resets the game & return players to Main Menu
     - resume button: resumes the game, you can also press ESC to do the same
     - controls button: will open a window that allows you to understand the controls + game
**End Screen**
* automatic bring up, 500 counter (will play when victory music ends) - game over options
  - restart button: resets the game & return players to Main Menu
  - quit button: closes the program and exits





**Note: Shield allows for temporary invulnerabilituy to enemy attacks + maintains own ability to attack**

## ✅ Working Features
* Background - main background, with the carpet seperate as an "item" to appear like character has a walk cycle + floor is carpet like
* Movement - able to complete basic movement, gravity + jump is working
* Controls - basic controls for abilities + movements, see [above for keybinds](https://github.com/EmperorMurfy/Cat_Attack/tree/main?tab=readme-ov-file#-controls)
* Sound Effects - graphicPanel function, playSound() - this function will play the sound with "filePath" once when called
* Music - playMusic.java, contains two functions, run() + close() - this function will play sound on loop until close()
* Character Stats Select - check above at controls for more info, able to change individual character stats for buffs/debuffs tradeoffs
* Health Bar - basic health bar, proned to be revamped ASAP
* Animations - check Sprite.java + ImageResource.java, able to call functions for animations 
* Shield - character ability to shield, temp-allows protection against attacks + retain ability to attack, has cooldown time that is not apparent visibily in game
* Attack - character ability to attack, currently one basic attack
* Win/Loss System - has a win + loss conditions, with respective screens, ends game
* Menu - basic menu with [play button] working
  -  Added pause game menu with [controls button] [restart game button] [resume button]
  - Added controls menu, allows players to take a look at control keybinds
  - Added game over options, allow users to restart or quit the game



## 🔮 Future Proposed Features
* Combo System - technically working in Simon branch, but bug encountered in merge attempt
* Sound Effects - certain sound effects being buggy, overlayed - walking sounds removed
* Damage Animation - removed due to a visual glitch, function .damage() 
* Menu Cont. - Ability to restart game and read game info, able to mute music + sfx, play custom menu music as well seperate from in-game music
* Health Bar - revamp health bar for both visual and functional purposes
* Profile Picture - add profile picture next to character to symbolize each character + current used stat
* Character Stats Select - prevent players from changing stats once in game 
* Character Select Screen - when adding more characters, and allows them to select their stats as they wish




## Credits:

**Developers:**
Simon N, Mason Z, Esan Y, Tyler G

**Artist:**
Alex F.

**Music Composer:**
Monica M.





