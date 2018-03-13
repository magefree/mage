#!/usr/bin/perl -w

#author: North

use Text::Template;
use strict;


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

sub toCamelCase {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    $string =~ s/[\/]//g;
    $string;
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
                #print ("$potentialSideA$val,,,$cardNumSideB\n");
                my @newCard;
                push (@newCard, "$potentialSideA$val");
                push (@newCard, "$setName");
                push (@newCard, "SPLIT");
                push (@newCard, @$ds[3]);
                push (@newCard, "$potentialSideA // $val");
                $cards{$newCard[0]}{$newCard[1]} = \@newCard;

                $cardNumSideB =~ s/.$//;
                push (@tempSetCards, "$potentialSideA$val,,,$cardNumSideB");

                #print ("Adding in: $potentialSideA \/\/ $val,,,$cardNumSideB\n");
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


my %authors;
my %author_counts;

foreach $name_collectorid (sort @setCards)
{
    my $cardName;
    my $cardNr;
    $name_collectorid =~ m/^(.*),,,(.*)$/; 
    $cardName = $1;
    $cardNr = $2;
    {
        my $ds;
        $ds = $cards{$cardName}{$setName};
        my $className = toCamelCase($cardName);
        my $setId = lc($cardName);
        $setId =~ s/^(.).*/$1/;
        my $fn = "..\\Mage.Sets\\src\\mage\\cards\\$setId\\$className.java";

        if (@$ds[2] eq "SPLIT") {
            my $oldCardName = $cardName;
            $cardName = @$ds[4];
        }

        my $plus_cardName = $cardName;
        $plus_cardName =~ s/ /+/img;
        $plus_cardName =~ s/,/+/img;
        $plus_cardName = "intext:\"$plus_cardName\"";
        my $cmd = "find /I \"\@author\" $fn";

        if (!exists ($alreadyIn{$cardNr})) {
            if (-e $fn) {
                my $output = `$cmd`;
                chomp $output;
                $output =~ s/^----.*\n//img;
                $output =~ s/^.*author\s*//img;
                $authors {$fn} = $output;
                $author_counts {$output} ++;

            }
        } else {
            if (-e $fn) {
                my $output = `$cmd`;
                chomp $output;
                $output =~ s/^----.*\n//img;
                $output =~ s/^.*.author\s*//img;
                $authors {$fn} = $output;
                $author_counts {$output} ++;

            }
        }
    }
}

print "\n\n\nAuthor Counts:\n";
my $author;
foreach $author (sort (keys %author_counts)) {
    if ($author !~ m/---/img) {
        print ("$author --> $author_counts{$author}");
    }
}

print ("\nData from reading: ../../mage/Mage.Sets/src/mage/sets/$knownSets{$setName}.java\n");
print "\n\nYou are done.  Press the enter key to exit.";
$setName = <STDIN>;
