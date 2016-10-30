#!/usr/bin/perl -w

#author: North

use Text::Template;
use strict;


my $authorFile = 'author.txt';
my $dataFile = "mtg-cards-data.txt";
my $setsFile = "mtg-sets-data.txt";
my $knownSetsFile = "known-sets.txt";


my %cards;
my %sets;
my %knownSets;

my @setCards;

# gets the set name
my $setName = $ARGV[0];
if(!$setName) {
    print 'Enter a set name: ';
    $setName = <STDIN>;
    chomp $setName;
}

my $template = Text::Template->new(TYPE => 'FILE', SOURCE => 'cardExtendedClass.tmpl', DELIMITERS => [ '[=', '=]' ]);
my $templateBasicLand = Text::Template->new(TYPE => 'FILE', SOURCE => 'cardExtendedLandClass.tmpl', DELIMITERS => [ '[=', '=]' ]);

sub toCamelCase {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    $string;
}

my $author;
if (-e $authorFile) {
    open (DATA, $authorFile);
    $author = <DATA>;
    close(DATA);
} else {
    $author = 'anonymous';
}

my $cardsFound = 0;
print ("Opening $dataFile\n");
open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $cards{$data[0]}{$data[1]} = \@data;

    if ($data[1] eq $setName) {
        my $cardInfo = "$data[0],,,$data[2]";
        push(@setCards, $cardInfo);
        $cardsFound = $cardsFound + 1;
    }
}
close(DATA);
print "Number of cards found for set " . $setName . ": " . $cardsFound . "\n";

open (DATA, $setsFile) || die "can't open $setsFile";

while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $sets{$data[0]}= $data[1];
}
close(DATA);

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[0]}= $data[1];
}
close(DATA);

my %raritiesConversion;
$raritiesConversion{'C'} = 'COMMON';
$raritiesConversion{'U'} = 'UNCOMMON';
$raritiesConversion{'R'} = 'RARE';
$raritiesConversion{'M'} = 'MYTHIC';
$raritiesConversion{'Special'} = 'SPECIAL';
$raritiesConversion{'Bonus'} = 'BONUS';
sub getRarity 
{
    my $val = $_ [0];
    if (exists ($raritiesConversion {$val}))
    {
        return $raritiesConversion {$val};
    }
    print ("JSKLDFJSLKDFJLSKJDF --- $val,,,$_[1]\n");
    sleep (10);
    return "WHOKNOWS";
}

# Generate the cards

my %vars;
$vars{'author'} = $author;
$vars{'set'} = $knownSets{$setName};
$vars{'expansionSetCode'} = $sets{$setName};

my $landForest = 0;
my $landMountain = 0;
my $landSwamp = 0;
my $landPlains = 0;
my $landIsland = 0;

print ("Reading in existing cards in set\n");
open (SET_FILE, "../../mage/Mage.Sets/src/mage/sets/$knownSets{$setName}.java") || die "can't open $dataFile";
my %alreadyIn;
while (<SET_FILE>) {
    my $line = $_;
    if ($line =~ m/SetCardInfo.*\("(.*)", (\d+).*/)
    {
        $alreadyIn {$2} = $1;
    }
}
close SET_FILE;

my $name_collectorid;
my %implemented;
my %implementedButNotInSetYet;
my %unimplemented;


my %githubTask;

foreach $name_collectorid (sort @setCards)
{
    my $cardName;
    my $cardNr;
    $name_collectorid =~ m/^(.*),,,(.*)$/; 
    $cardName = $1;
    $cardNr = $2;
    {
        if($cardName eq "Forest" || $cardName eq "Island" || $cardName eq "Plains" || $cardName eq "Swamp" || $cardName eq "Mountain") {
            my $found = 0;
            if ($cardName eq "Forest") {
                $landForest++;
            }
            if ($cardName eq "Mountain") {
                $landMountain++;
            }
            if ($cardName eq "Swamp") {
                $landSwamp++;
            }
            if ($cardName eq "Plains") {
                $landPlains++;
            }
            if ($cardName eq "Island") {
                $landIsland++;
            }
            if (!exists ($alreadyIn{$cardNr})) {
                print ("        cards.add(new SetCardInfo(\"$cardName\", $cardNr, Rarity.LAND, mage.cards.basiclands.$cardName.class, new CardGraphicInfo(null, true)));\n");
            }
        }
        else {
            my $className = toCamelCase($cardName);
            my $setId = lc($cardName);
            $setId =~ s/^(.).*/$1/;
            my $fn = "..\\Mage.Sets\\src\\mage\\cards\\$setId\\$className.java";
            my $str = "        cards.add(new SetCardInfo(\"$cardName\", $cardNr, Rarity." . getRarity ($cards{$cardName}{$setName}[3], $cardName) . ", mage.cards.$setId.$className.class));\n";
            my $plus_cardName = $cardName;
            $plus_cardName =~ s/ /+/img;

            if (!exists ($alreadyIn{$cardNr})) {
# Go Looking for the existing implementation..
                if (-e $fn) {
                    $implementedButNotInSetYet {$str} = 1;
                    $githubTask {"- [ ] Implemented but have to add to set -- [$cardName](https://www.google.com.au/search?q=$plus_cardName+$setName+mtg&source=lnms&tbm=isch)\n"} = 1;
                } else { 
                    $unimplemented {"$str"} = 1;
                    $githubTask {"- [ ] Not done -- [$cardName](https://www.google.com.au/search?q=$plus_cardName+$setName+mtg&source=lnms&tbm=isch)\n"} = 1;
                }
            } else {
                if (-e $fn) {
                    $implemented {$str} = 1;
                    $githubTask {"- [x] Done -- [$cardName](https://www.google.com.au/search?q=$plus_cardName+$setName+mtg&source=lnms&tbm=isch)\n"} = 1;
                } else { 
                    $unimplemented {$str} = 1;
                    $githubTask {"- [ ] Not Done -- [$cardName](https://www.google.com.au/search?q=$plus_cardName+$setName+mtg&source=lnms&tbm=isch)\n"} = 1;
                }
            }
        }
    }
}

print "Implemented cards:\n";
print (join ("", sort keys (%implemented)));
print "\n\n\nImplemented but-not-yet-added-to-set cards:\n";
print (join ("", sort keys (%implementedButNotInSetYet)));
print "\n\n\nUnimplemented cards:\n";
print (join ("", sort keys (%unimplemented)));
print "\n\n\nGithub Task:\n";
print (join ("", sort keys (%githubTask)));
print ("\nData from reading: ../../mage/Mage.Sets/src/mage/sets/$knownSets{$setName}.java\n");
