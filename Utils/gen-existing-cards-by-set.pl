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
my %all_sets;

print ("Opening $dataFile\n");
open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $cards{$data[0]}{$data[1]} = \@data;

    if ($data[1] eq $setName) {
        my $cardInfo = "$data[0],,,$data[2]";

        push(@setCards, $cardInfo);
    } else { 
        $all_sets {$data[1]} = 1;
    }
}

# Fix up split cards
my $potentialSideA;
my @tempSetCards;

foreach $potentialSideA (sort @setCards) {
    #print (">>$potentialSideA\n"); 
    if ($potentialSideA =~ m/.*,,,(\d+)(a)$/) {
        my $cardNumSideB = $1 . "b";
        my $val;
        foreach $val (sort @setCards) {
            if ($val =~ m/$cardNumSideB$/) {
                $potentialSideA =~ s/,,,.*//;
                $val =~ s/,,,.*//;

                # Add 'SideaSideb' to %cards
                my $ds = $cards{$val}{$setName};
                print ("$potentialSideA$val,,,$cardNumSideB\n");
                my @newCard;
                push (@newCard, "$potentialSideA$val");
                push (@newCard, "$setName");
                push (@newCard, "SPLIT");
                push (@newCard, @$ds[3]);
                push (@newCard, "$potentialSideA // $val");
                $cards{$newCard[0]}{$newCard[1]} = \@newCard;

                $cardNumSideB =~ s/.$//;
                push (@tempSetCards, "$potentialSideA$val,,,$cardNumSideB");

                print ("Adding in: $potentialSideA \/\/ $val,,,$cardNumSideB\n");
                $cardsFound = $cardsFound - 1;
            }
        }
    }
    elsif ($potentialSideA =~ m/.*,,,(\d+)(b)$/) {
        next;
    }
    else {
        $cardsFound = $cardsFound + 1;
        push (@tempSetCards, $potentialSideA);
    }
}
@setCards = @tempSetCards;

close(DATA);
print "Number of cards found for set " . $setName . ": " . $cardsFound . "\n";


if ($cardsFound == 0) {
    $setName =~ s/^(...).*/$1/;
    my $poss;
    my $foundPossibleSet = 0;
    my $numPossibleSets = 0;
    foreach $poss (sort keys (%all_sets)) {
        $numPossibleSets++;
        if ($poss =~ m/^$setName/i) {
            print ("Did you possibly mean: $poss ?\n");
            $foundPossibleSet = 1;
        }
    }
    if (!$foundPossibleSet) {
        print ("Couldn't find any matching set for '$setName'. \n");
    }
    
    print "(Note: Looked at $numPossibleSets sets in total).\nPress the enter key to exit.";
    $setName = <STDIN>;
    exit;
}

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
    print ("ERROR DETECTED! - Incorrect rarity.. --- $val,,,$_[1]\n");
    sleep (10);
    print "Press the enter key to exit.";
    $setName = <STDIN>;
    exit;
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
            my $ds;
            $ds = $cards{$cardName}{$setName};
            my $className = toCamelCase($cardName);
            my $setId = lc($cardName);
            $setId =~ s/^(.).*/$1/;
            my $googleSetName = $setName;
            $googleSetName =~ s/ /+/img;
            my $fn = "..\\Mage.Sets\\src\\mage\\cards\\$setId\\$className.java";
            my $str = "        cards.add(new SetCardInfo(\"$cardName\", $cardNr, Rarity." . getRarity ($cards{$cardName}{$setName}[3], $cardName) . ", mage.cards.$setId.$className.class));\n";

            if (@$ds[2] eq "SPLIT") {
                my $oldCardName = $cardName;
                $cardName = @$ds[4];
                $str = "        cards.add(new SetCardInfo(\"$cardName\", $cardNr, Rarity." . getRarity ($cards{$oldCardName}{$setName}[3], $oldCardName) . ", mage.cards.$setId.$className.class));\n";
            }

            my $plus_cardName = $cardName;
            $plus_cardName =~ s/ /+/img;
            $plus_cardName =~ s/,/+/img;
            $plus_cardName = "intext:\"$plus_cardName\"";

            if (!exists ($alreadyIn{$cardNr})) {
# Go Looking for the existing implementation..
                if (-e $fn) {
                    $implementedButNotInSetYet {$str} = 1;
                    $githubTask {"- [ ] Implemented but have to add to set -- [$cardName](https://www.google.com.au/search?q=$plus_cardName+$googleSetName+mtg&source=lnms&tbm=isch)\n"} = 1;
                } else { 
                    $unimplemented {"$str"} = 1;
                    $githubTask {"- [ ] Not done -- [$cardName](https://www.google.com.au/search?q=$plus_cardName+$googleSetName+mtg&source=lnms&tbm=isch)\n"} = 1;
                }
            } else {
                if (-e $fn) {
                    $implemented {$str} = 1;
                    $githubTask {"- [x] Done -- [$cardName](https://www.google.com.au/search?q=$plus_cardName+$googleSetName+mtg&source=lnms&tbm=isch)\n"} = 1;
                } else { 
                    $unimplemented {$str} = 1;
                    $githubTask {"- [ ] Not Done -- [$cardName](https://www.google.com.au/search?q=$plus_cardName+$googleSetName+mtg&source=lnms&tbm=isch)\n"} = 1;
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
print "\n\nYou are done.  Press the enter key to exit.";
$setName = <STDIN>;
