"""
Purpose: Removes duplicate CardName|CardSet|CardNumber| entries from mtg-cards-data.txt that crop up

@author: escplan9 (Derek Monturo - dmontur1 at gmail dot com)
@version: 1.0

Written in Python 3.x, should work in Python 2.x as well.
"""
import re

"""
example line from file:
Aven Mimeomancer|Alara Reborn|2|R|{1}{W}{U}|Creature - Bird Wizard|3|1|Flying$At the beginning of your upkeep, you may put a feather counter on target creature. If you do, that creature is 3/1 and has flying for as long as it has a feather counter on it.|

With the reg-ex pattern below, separates into 2 match groups
match-group-1: Aven Mimeomancer|Alara Reborn|2|
match-group-2: (remainder of the line up to the \r\n)
"""
reg_ptn = re.compile(r'([a-zA-Z].*[|][a-zA-Z].*[|]\d+[|])(.*[\r\n])')
orig_txt_filename = 'mtg-cards-data.txt'

card_dict = {}
new_contents = ''

with open(orig_txt_filename) as orig_txt_file:
	for line in orig_txt_file:
		matchObj = re.match(reg_ptn, line)
		if matchObj is not None:
			matchGroups = matchObj.groups()
			if len(matchGroups) > 0:
				card_set_num = matchGroups[0]
				if card_set_num not in card_dict.keys(): # only add unique card-set-number entries to new-contents
					card_dict[card_set_num] = True
					new_contents += line
			else:
				new_contents += line
		else:
			new_contents += line

# generate new file with the de-duped contents
new_txt_filename = 'unduped-cards-data.txt'
with open(new_txt_filename, "w") as new_txt_file:
	new_txt_file.write(new_contents)