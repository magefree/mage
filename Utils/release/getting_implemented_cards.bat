1. Go to Mage Repository
2. Run command exemplified below:

For cards added since tagOrSha1 till head revision (you can replace HEAD with another tagOrSha1):
git log tagOrSha1..HEAD --diff-filter=A --name-status | sed -ne 's/^A[^u]Mage.Sets\/src\/mage\/sets\///p' | sort -u > added_cards.txt

3. Copy added_cards.txt to trunk\Utils folder
4. Run script:
> perl extract_in_wiki_format.perl
5. Open added_cards_in_wiki_format.txt
6. Copy content to release notes