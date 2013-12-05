Usage:
gen-card.pl - enter the card name when prompted to generate the java classes
gen-existing-cards-by-set.pl - generates the java clases for the cards from the set of your choice that already have an implementation
gen-simple-cards-by-set.pl - generates the java clases for the cards from the set of your choice that can be completly generated
update-list-implemented-cards.pl
 - generates
  - oldList.txt: list of cards implemented at the time the script is ran
  - newList.txt: list of cards implemented since the last time the script was ran
gen-list-cards-for-set.pl - generates the file for cards for a set
gen-list-unimplemented-cards-for-set.pl - generates the file for unimplemented cards for a set
  
Files used:
 - author.txt - one line file that contains the author name you want to appear in the generated java files
 - keywords.txt - list of keywords that have an implementation and are automatically added to the card implementation
 - known-sets.txt - list of sets used in mage (Set name and Package name)
 - mtg-cards-data.txt - MTG cards data (generated from the Gatherer database)
 - mtg-sets-data.txt - list of sets in MTG and the 3 letters code for each of them