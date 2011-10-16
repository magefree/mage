1. Go to folder trunk\Mage.Sets\src\mage\sets
2. Run command for revision or range of revisions:

Examples:

For cards added since r1494 till head revision:
hg status --rev 1494 -a --exclude "*\Island[0-9].java" --exclude "*\Mountain[0-9].java" --exclude "*\Swamp[0-9].java" --exclude "*\Plains[0-9].java" --exclude "*\Forest[0-9].java" --include "*\*.java" > added_cards.txt

For cards added since r1494 till r1550:
just use --rev 1494:1550 instead

3. Copy added_cards.txt to trunk\Utils folder
4. Run script:
> perl extract_in_wiki_format.perl
5. Open added_cards_in_wiki_format.txt
6. Copy content to release notes