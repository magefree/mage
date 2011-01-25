#!/usr/bin/perl -w

use WWW::Mechanize;
use HTML::TreeBuilder;
use Text::Template;
use Data::Dumper;
use strict;

my $datafile = "mtg-cards-data.txt";

my @sets;
my @collector;
my %rarities; 
my %collectors;
my @multiverses;

my $manacost = "";
my $loyality = -1;
my $power = -1;
my $toughness = -1;
my @texts;
my @types;

my %fulltoshort;

$fulltoshort{'Tenth Edition'} = '10E';
$fulltoshort{'Unlimited Edition'} = '2ED';
$fulltoshort{'Revised Edition'} = '3ED';
$fulltoshort{'Fourth Edition'} = '4ED';
$fulltoshort{'Fifth Dawn'} = '5DN';
$fulltoshort{'Fifth Edition'} = '5ED';
$fulltoshort{'Classic Sixth Edition'} = '6ED';
$fulltoshort{'Seventh Edition'} = '7ED';
$fulltoshort{'Eighth Edition'} = '8ED';
$fulltoshort{'Alliances'} = 'ALL';
$fulltoshort{'Apocalypse'} = 'APC';
$fulltoshort{'Alara Reborn'} = 'ARB';
$fulltoshort{'Arabian Nights'} = 'ARN';
$fulltoshort{'Anthologies'} = 'ATH';
$fulltoshort{'Antiquities'} = 'ATQ';
$fulltoshort{'Betrayers of Kamigawa'} = 'BOK';
$fulltoshort{'Battle Royale Box Set'} = 'BRB';
$fulltoshort{'Beatdown Box Set'} = 'BTD';
$fulltoshort{'Champions of Kamigawa'} = 'CHK';
$fulltoshort{'Chronicles'} = 'CHR';
$fulltoshort{'Conflux'} = 'CON';
$fulltoshort{'Coldsnap'} = 'CSP';
$fulltoshort{'Darksteel'} = 'DST';
$fulltoshort{'Dissension'} = 'DIS';
$fulltoshort{'Deckmasters'} = 'DKM';
$fulltoshort{'The Dark'} = 'DRK';
$fulltoshort{'Darksteel'} = 'DST';
$fulltoshort{'Duel Decks: Divine vs. Demonic'} = 'DVD';
$fulltoshort{'Duel Decks: Elves vs. Goblins'} = 'EVG';
$fulltoshort{'Duel Decks: Garruk vs. Liliana'} = 'GVL';
$fulltoshort{'Duel Decks: Jace vs. Chandra'} = 'JVC';
$fulltoshort{'Duel Decks: Phyrexia vs. the Coalition'} = 'PVC';
$fulltoshort{'Eventide'} = 'EVE';
$fulltoshort{'Exodus'} = 'EXO';
$fulltoshort{'Fallen Empires'} = 'FEM';
$fulltoshort{'Future Sight'} = 'FUT';
$fulltoshort{'From the Vault: Dragons'} = 'FVD';
$fulltoshort{'From the Vault: Exiled'} = 'FVE';
$fulltoshort{'Guildpact'} = 'GPT';
$fulltoshort{'Homelands'} = 'HML';
$fulltoshort{'Planechase'} = 'HOP';
$fulltoshort{'Ice Age'} = 'ICE';
$fulltoshort{'Invasion'} = 'INV';
$fulltoshort{'Judgment'} = 'JUD';
$fulltoshort{'Limited Edition Alpha'} = 'LEA';
$fulltoshort{'Limited Edition Beta'} = 'LEB';
$fulltoshort{'Legends'} = 'LEG';
$fulltoshort{'Legions'} = 'LGN';
$fulltoshort{'Lorwyn'} = 'LRW';
$fulltoshort{'Magic 2010'} = 'M10';
$fulltoshort{'Magic 2011'} = 'M11';
$fulltoshort{'Masters Edition II'} = 'ME2';
$fulltoshort{'Masters Edition III'} = 'ME3';
$fulltoshort{'Masters Edition'} = 'MED';
$fulltoshort{'Mirage'} = 'MIR';
$fulltoshort{'Mercadian Masques'} = 'MMQ';
$fulltoshort{'Morningtide'} = 'MOR';
$fulltoshort{'Mirrodin'} = 'MRD';
$fulltoshort{'Ninth Edition'} = '9ED';
$fulltoshort{'Nemesis'} = 'NEM';
$fulltoshort{'Odyssey'} = 'ODY';
$fulltoshort{'Onslaught'} = 'ONS';
$fulltoshort{'Portal Second Age'} = 'PO2';
$fulltoshort{'Prophecy'} = 'PCY';
$fulltoshort{'Planar Chaos'} = 'PLC';
$fulltoshort{'Planeshift'} = 'PLS';
$fulltoshort{'Portal'} = 'POR';
$fulltoshort{'Portal Three Kingdoms'} = 'PTK';
$fulltoshort{'Premium Deck Series: Slivers'} = 'PDS';
$fulltoshort{'Ravnica: City of Guilds'} = 'RAV';
$fulltoshort{'Rise of the Eldrazi'} = 'ROE';
$fulltoshort{'Starter 2000'} = 'S00';
$fulltoshort{'Starter 1999'} = 'S99';
$fulltoshort{'Scourge'} = 'SCG';
$fulltoshort{'Shadowmoor'} = 'SHM';
$fulltoshort{'Shards of Alara'} = 'ALA';
$fulltoshort{'Saviors of Kamigawa'} = 'SOK';
$fulltoshort{'Stronghold'} = 'STH';
$fulltoshort{'Tempest'} = 'TMP';
$fulltoshort{'Torment'} = 'TOR';
$fulltoshort{'Time Spiral "Timeshifted"'} = 'TSB';
$fulltoshort{'Time Spiral'} = 'TSP';
$fulltoshort{'Urza\'s Destiny'} = 'UDS';
$fulltoshort{'Unglued'} = 'UGL';
$fulltoshort{'Urza\'s Legacy'} = 'ULG';
$fulltoshort{'Unhinged'} = 'UNH';
$fulltoshort{'Urza\'s Saga'} = 'USG';
$fulltoshort{'Vanguard Set 1'} = 'VG1';
$fulltoshort{'Vanguard Set 2'} = 'VG2';
$fulltoshort{'Vanguard Set 3'} = 'VG3';
$fulltoshort{'Vanguard Set 4'} = 'VG4';
$fulltoshort{'MTGO Vanguard'} = 'VGO';
$fulltoshort{'Visions'} = 'VIS';
$fulltoshort{'Weatherlight'} = 'WTH';
$fulltoshort{'Worldwake'} = 'WWK';
$fulltoshort{'Zendikar'} = 'ZEN';
$fulltoshort{'Archenemy'} = 'ARC';
$fulltoshort{'Scars of Mirrodin'} = 'SOM';

my %wizardstous;
$wizardstous{'6E'} = '6ED';
$wizardstous{'7E'} = '7ED';
$wizardstous{'8ED'} = '8ED';
$wizardstous{'9ED'} = '9ED';
$wizardstous{'10E'} = '10E';
$wizardstous{'BD'} = 'BTD';
$wizardstous{'DDD'} = 'GVL';
$wizardstous{'CG'} = 'UDS';
$wizardstous{'ST'} = 'STH';
$wizardstous{'ONS'} = 'ONS';
$wizardstous{'P3'} = 'S99';
$wizardstous{'P4'} = 'S00';
$wizardstous{'OD'} = 'ODY';
$wizardstous{'M10'} = 'M10';
$wizardstous{'M11'} = 'M11';
$wizardstous{'LRW'} = 'LRW';
$wizardstous{'DD2'} = 'JVC';
$wizardstous{'TSB'} = 'TSB';
$wizardstous{'ZEN'} = 'ZEN';
$wizardstous{'5E'} = '5ED';
$wizardstous{'IA'} = 'ICE';
$wizardstous{'4E'} = '4ED';
$wizardstous{'3E'} = '3ED';
$wizardstous{'2U'} = '2ED';
$wizardstous{'2E'} = 'LEB';
$wizardstous{'1E'} = 'LEA';
$wizardstous{'EVG'} = 'EVG';
$wizardstous{'BR'} = 'BRB';
$wizardstous{'ME2'} = 'ME2';
$wizardstous{'ME3'} = 'ME3';
$wizardstous{'HOP'} = 'HOP';
$wizardstous{'PO'} = 'POR';
$wizardstous{'P2'} = 'PO2';
$wizardstous{'PK'} = 'PTK';
$wizardstous{'MED'} = 'MED';
$wizardstous{'EVE'} = 'EVE';
$wizardstous{'MOR'} = 'MOR';
$wizardstous{'RAV'} = 'RAV';
$wizardstous{'SHM'} = 'SHM';
$wizardstous{'MI'} = 'MIR';
$wizardstous{'ARB'} = 'ARB';
$wizardstous{'VI'} = 'VIS';
$wizardstous{'DST'} = 'DST';
$wizardstous{'TOR'} = 'TOR';
$wizardstous{'DDC'} = 'DVD';
$wizardstous{'AP'} = 'APC';
$wizardstous{'CON'} = 'CON';
$wizardstous{'ALA'} = 'ALA';
$wizardstous{'DDE'} = 'PVC';
$wizardstous{'JUD'} = 'JUD';
$wizardstous{'WL'} = 'WTH';
$wizardstous{'MRD'} = 'MRD';
$wizardstous{'TE'} = 'TMP';
$wizardstous{'DRB'} = 'FVD';
$wizardstous{'PR'} = 'PCY';
$wizardstous{'UZ'} = 'USG';
$wizardstous{'IN'} = 'INV';
$wizardstous{'FUT'} = 'FUT';
$wizardstous{'TSP'} = 'TSP';
$wizardstous{'CHK'} = 'CHK';
$wizardstous{'EX'} = 'EXO';
$wizardstous{'FE'} = 'FEM';
$wizardstous{'PLC'} = 'PLC';
$wizardstous{'CHK'} = 'CHK';
$wizardstous{'ROE'} = 'ROE';
$wizardstous{'5DN'} = '5DN';
$wizardstous{'LE'} = 'LEG';
$wizardstous{'CH'} = 'CHR';
$wizardstous{'H09'} = 'PDS';
$wizardstous{'MM'} = 'MMQ';
$wizardstous{'GPT'} = 'GPT';
$wizardstous{'GU'} = 'ULG';
$wizardstous{'ARC'} = 'ARC';
$wizardstous{'DIS'} = 'DIS';
$wizardstous{'NE'} = 'NEM';
$wizardstous{'PS'} = 'PLS';
$wizardstous{'LGN'} = 'LGN';
$wizardstous{'AN'} = 'ARN';
$wizardstous{'WWK'} = 'WWK';
$wizardstous{'SOM'} = 'SOM';

my %knownSets;
$knownSets{'ARB'} = 'alarareborn';
$knownSets{'CON'} = 'conflux';
$knownSets{'M10'} = 'magic2010';
$knownSets{'M11'} = 'magic2011';
$knownSets{'HOP'} = 'planechase';
$knownSets{'RAV'} = 'ravnika';
$knownSets{'ROE'} = 'riseoftheeldrazi';
$knownSets{'ALA'} = 'shardsofalara';
$knownSets{'10E'} = 'tenth';
$knownSets{'WWK'} = 'worldwake';
$knownSets{'ZEN'} = 'zendikar';
$knownSets{'SOM'} = 'scarsofmirrodin';
$knownSets{'GPT'} = 'guildpact';
$knownSets{'DIS'} = 'dissension';

my %raritiesConversion;
$raritiesConversion{'C'} = 'COMMON';
$raritiesConversion{'U'} = 'UNCOMMON';
$raritiesConversion{'R'} = 'RARE';
$raritiesConversion{'M'} = 'MYTHIC';

my %mana;
$mana{'Black'} = '{B}';
$mana{'Blue'} = '{U}';
$mana{'Green'} = '{G}';
$mana{'Red'} = '{R}';
$mana{'White'} = '{W}';
$mana{'Variable Colorless'} = '{X}';
$mana{'White or Black'} = 'W\\\\B';
$mana{'Green or White'} = 'G\\\\W';
$mana{'Black or Green'} = 'B\\\\G';
$mana{'Black or Red'} = 'B\\\\R';
$mana{'Red or Green'} = 'R\\\\G';

my %manatocolor;
$manatocolor{'Black'} = "		this.color.setBlack(true);";
$manatocolor{'Blue'} = "		this.color.setBlue(true);";
$manatocolor{'Green'} = "		this.color.setGreen(true);";
$manatocolor{'Red'} = "		this.color.setRed(true);";
$manatocolor{'White'} = "		this.color.setWhite(true);";

my %cardtypes;
$cardtypes{'Artifact'} = "CardType.ARTIFACT";
$cardtypes{'Creature'} = "CardType.CREATURE";
$cardtypes{'Instant'} = "CardType.INSTANT";
$cardtypes{'Sorcery'} = "CardType.SORCERY";

my %normalid;

print "Enter a card name: ";
my $cardname = <STDIN>;
chomp $cardname;
my $finded = 0;

open DATA, "< $datafile" or die "Can't open datafile: $!";
while (<DATA>) {
	my $str = $_;
	my (undef, $name, $set, $rarity, $multiverse, $collector) = split("\\|");
	if ($cardname eq $name) {
	    die "can't find set: $set" unless defined $fulltoshort{$set};
	    push(@sets, $fulltoshort{$set});
	    $rarities{$fulltoshort{$set}} = $rarity;
	    $collectors{$fulltoshort{$set}} = $collector;
	    push(@multiverses, $multiverse);
	    $finded = 1;
	}
}

die "card not found" unless $finded;

my $mech = WWW::Mechanize->new( autocheck => 1 );
my $parser = HTML::TreeBuilder->new();

$ENV{'HTTP_PROXY'} = 'http://login:password@proxyserver:port';
$mech->env_proxy();

$mech->get("http://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=" . $multiverses[0] );
print "card fetched\n";

my $template = Text::Template->new(SOURCE => 'cardclass.tmpl', DELIMITERS => [ '[=', '=]' ]);
my %vars;
$vars{'name'} = $cardname;
$cardname =~ s/[-\s\']//g;
$vars{'classname'} = $cardname;


$parser->parse($mech->content());
my @divs = $parser->look_down('_tag', 'div');
foreach my $div (@divs) {
    if (defined($div->attr('id'))) {
	my $id = $div->attr('id');
	if ($id =~m/textRow/) {
	    foreach my $sub ($div->look_down('_tag', 'div')) {
		if (defined($sub->attr('class')) && $sub->attr('class') eq 'cardtextbox') {
		    push(@texts, $sub->as_text());
		}
	    }
	}
	if ($id =~m/typeRow/) {
	    my $typestring = "";
	    my $subtype = "";
	    foreach my $sub ($div->look_down('_tag', 'div')) {
			if (defined($sub->attr('class')) && $sub->attr('class') eq 'value') {
		    	my $type = $sub->as_text();
		    	chomp $type;
		    	while ( $type =~ m/([a-zA-z]+)( )*/g ) {
		        	push @types, $1;
		        	if (exists($cardtypes{$1})) {
                        if (length($typestring) > 0) {
                            $typestring .= ", " . $cardtypes{$1};
                        } else {
                            $typestring = $cardtypes{$1};
                        }
		        	} else {
						$subtype .= "        this.subtype.add(\"$1\");\n";
					}
		    	}
			}
	    }
	    $vars{'type'} = $typestring;
		$vars{'subtype'} = $subtype;
	}
	if ($id =~m/manaRow/) {
	    my $findedcolors;
	    foreach my $sub ($div->look_down('_tag', 'img')) {	    	
		if (defined($sub->attr('alt'))) {
			my $m = $sub->attr('alt'); 		
			if ($m =~ /^-?\d/) {
				$manacost .= "{" . $m . "}";
			} else { 				
		    	die "unknown manacost: " . $m unless defined $mana{$m};
		    	$manacost .= $mana{$m};
		    	$findedcolors .= "\n" . $manatocolor{$m};  		    	
			}
		}
	    }
	    $manacost =~ s/^\s*(\S*(?:\s+\S+)*)\s*$/$1/; 
	    $vars{'manacost'} = $manacost;	    
	    $vars{'colors'} = $findedcolors;
		print Dumper(%vars);
	}
	if ($id =~/ptRow/) {
		foreach my $sub ($div->look_down('_tag', 'div')) {
			if (defined($sub->attr('class')) && $sub->attr('class') eq 'value') {
				my $str = $sub->as_text();
				$str =~s/\s//;
				$str =~m/(.+)\/(.+)/;
				$vars{'power'} = $1;
				$vars{'toughness'} = $2;
			}
		}
	}
	if ($id =~m/currentSetSymbol/) {
		my ($imgurl) = $div->look_down('_tag', 'img');
		$imgurl->attr('src') =~m/set=(\w+)\&.*rarity=(\w+)/;
		my $multiverseid = $multiverses[0];
		die "can't find set conversion for $1, multiverse: $multiverseid" unless defined $wizardstous{$1};
		my $set = $wizardstous{$1};
		$normalid{$set} = $multiverseid;
	}
	if ($id =~m/otherSetsValue/) {
	    foreach my $sub ($div->look_down('_tag', 'a')) {
		my $link = $sub->attr('href');
		$link =~m/multiverseid\=(\d+)/;
		my $multiverseid = $1;
		my ($imgurl) = $sub->look_down('_tag', 'img');
		$imgurl->attr('src') =~m/set=(\w+)\&.*rarity=(\w+)/;
		die "can't find set conversion for $1, multiverse: $multiverseid" unless defined $wizardstous{$1};
		my $set = $wizardstous{$1};
		$normalid{$set} = $multiverseid;
	    }
	}
    }
}


for my $set (@sets) {
    if (exists($knownSets{$set})) {
	$vars{'longset'} = $knownSets{$set};
	$vars{'rarity'} = $raritiesConversion{$rarities{$set}};
	$vars{'collector'} = $collectors{$set};
	$vars{'setcode'} = $set;
        my $result = $template->fill_in(HASH => \%vars);
	if (defined($result)) {
	    my $filename = "../Mage.Sets/src/mage/sets/". $knownSets{$set} . "/" . $vars{'classname'} . ".java";
	    if (-e $filename ) {
		print "WARNING $filename already exists!\n";
	    } else {
		open CARD, "> $filename";
		print CARD $result; 
		close CARD;
	    }
	}
    }
}