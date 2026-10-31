Usage:
check-set-printings.py <SET_CODE> - lists implemented cards whose printing (collector number) is not registered in the set's Java class
gen-card.pl - enter the card name when prompted to generate the java classes
gen-draft-rates-17Lands.py - generates draft ratings for AI/bots (Mage/src/main/resources/ratings) from 17Lands public datasets, run without params for sets table and menu
gen-existing-cards-by-set.pl - generates the java clases for the cards from the set of your choice that already have an implementation
gen-list-cards-for-set.pl - generates the file for cards for a set in data/
gen-list-unimplemented-cards-for-set.pl - generates the file for unimplemented cards for a set in data/
mtg-cards-data-scryfall.py - generates mtg-cards-data.txt based on Scryfall

Files used:
 - keywords.txt - list of keywords that have an implementation and are automatically added to the card implementation
 - mtg-cards-data.txt - MTG cards data, used for card implementation trackers and generating release notes
 - mtg-sets-data.txt - list of sets in MTG and their internal set code
 - data/author.txt - one line file that contains the author name you want to appear in the generated java files
 - templates/ - templates used by scripts in this folder

Some scripts fetch or generate files needed after the script finishes. Store those files in Utils/data, which is ignored by Git.

If files are only needed during a single run, keep them in memory or write them to the OS temporary directory so they are cleaned up and do not pollute the repository.
