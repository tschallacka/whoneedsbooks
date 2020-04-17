Who Needs Books Anyways

===============

Adding spells books to Minecraft. What's more to say.

# How to use

* With CrafTweaker or /give command one can create a new spellbook with added tags to configure.

Example:
```
<whoneedsbooks:spellbook_novice>.withTag({t4: 3, t5: 4, debug: 0 as byte, RepairCost: 0, display: {Name: "Book of Myst"}, c1: "/particle explode ~ ~ ~ 5 5 5 0.1 250", c2: "/playsound botania:enchanterenchant player @p", c3: "/effect @p tombstone:g
```

* In creative mode a spell book when right clicked will display a gui for entering commands, delay timing in ticks, and cool down time

# Contributing rules

* If you wish to send a pull request, do it to the develop branch, not the master.  

Licensing. Because new European rules concerning copyright will come in effect the following rules will have to be
obeyed when submitting a pull request, because otherwise I cannot use your contributions due to lacking paper trail. I need documentation/proof that I am allowed to use your contribution. Hence the following list I require you to adhere to when contributing.

* All your code contributions must be licensed under MIT or compatible. 
* Add a MIT license file in the licensed folder with your license header. 
* All your textual/image contributions must be licensed under CC-BY-2.0 https://creativecommons.org/licenses/by/2.0/ or compatible variants.
* You must add your name to the CONTRIBUTORS.MD in the relevant section if you send a pull request for a fix and your name is not there yet.  
* When you submit a pull request, mention in the description of the pull request that this pull request is licensed under either MIT or CC-BY
* If you submit mixed licensed content specify which parts of the pull request are licensed under which license in the description.
* Or you can list the files that are licensed by you under CC-BY-2.0 or comparable under CONTRIBUTORS.md with author name and required data if you are not the original author of the contributed file(See Ironcat in CONTRIBUTORS.md for an example). 
* Do not submit content that is not under a permissive license. If you submit 3rd party content make sure it is licensed and attributed correctly
* If you use public domain content license it under CC0.

In short: Just make sure to define in some way what license is applicable to what you contributed. Otherwise your pull request will get a comment for clarification and not merged due to copyright law requirements, so this mod won't get burned through Github content filters. I need a paper trail.
