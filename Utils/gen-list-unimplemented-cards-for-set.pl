#!/usr/bin/perl -w

#author: North

use strict;
use Scalar::Util qw(looks_like_number);

my $dataFile = "mtg-cards-data.txt";
my $setsFile = "mtg-sets-data.txt";
my $knownSetsFile = "known-sets.txt";

my %sets;
my %knownSets;

my @setCards;

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[0]} = $data[1];
    #print ("$data[0] ===> $data[1]\n");
}
close(DATA);


# gets the set name
my $setName = $ARGV[0];
if(!$setName) {
    print 'Enter a set name: ';
    $setName = <STDIN>;
    chomp $setName;
    $setName = $setName;
}

while (!defined ($knownSets{$setName}))
{
    print ("Invalid set - '$setName'\n");
    print ("  Possible sets you meant:\n");
    my $origSetName = $setName;
    $setName =~ s/^(.).*/$1/;
    my $key;
    foreach $key (sort keys (%knownSets))
    {
        if ($key =~ m/^$setName/img)
        {
            print ("   '$key'\n");
        }
    }

    print 'Enter a set name: ';
    $setName = <STDIN>;
    $setName = $setName;
    chomp $setName;
}


open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    if ($data[1] eq $setName) {
        push(@setCards, \@data);
    }
}
close(DATA);

open (DATA, $setsFile) || die "can't open $setsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $sets{$data[0]}= $data[1];
}
close(DATA);


sub cardSort {
  if (!looks_like_number(@{$a}[2])) { return -1; }
  if (!looks_like_number(@{$b}[2])) { return 1; }
  if (@{$a}[2] < @{$b}[2]) { return -1; }
  elsif (@{$a}[2] == @{$b}[2]) { return 0;}
  elsif (@{$a}[2] > @{$b}[2]) { return 1; }
}

sub toCamelCase {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    $string;
}

# TODO: check for basic lands with ending 1,2,3,4,5 ...
my %cardNames;
my $toPrint = '';
my $setAbbr = $sets{$setName};
foreach my $card (sort cardSort @setCards) {
	my $className = toCamelCase(@{$card}[0]);

    $cardNames {@{$card}[0]} = 1;

    my $currentFileName = "../Mage.Sets/src/mage/cards/" . lc(substr($className, 0, 1)) . "/" . $className . ".java";
	if(! -e $currentFileName) {
        $cardNames {@{$card}[0]} = 0;
		if ($toPrint) {
			$toPrint .= "\n";
		}
                my $cardName = @{$card}[0];
                $cardName =~ s/ /+/g;
		$toPrint .= "@{$card}[2]|[@{$card}[0]](https://scryfall.com/search?q=!\"$cardName\"&nbsp;e:$setAbbr)";
	}
}

open CARD, "> " . lc($sets{$setName}) ."_unimplemented.txt";
print CARD $toPrint;
close CARD;


print ("Unimplemented cards are here: " . lc($sets{$setName}) ."_unimplemented.txt\n");

open ISSUE_TRACKER, "> " . lc($sets{$setName}) ."_issue_tracker.txt";
print ISSUE_TRACKER "# Cards in set:\n";


my $cn;
foreach $cn (sort keys (%cardNames))
{
    my $x_or_not = "[ ]";
    if ($cardNames {$cn} == 1)
    {
        $x_or_not = "[x]";
    }
    my $cn2 = $cn;
    $cn2 =~ s/ /+/g;
    print ISSUE_TRACKER "- $x_or_not [$cn](https://scryfall.com/search?q=!\"$cn2\"&nbsp;e:$setAbbr)\n";
}
close ISSUE_TRACKER;
print ("Tracking Issue text for a new Github issue (similar to https://github.com/magefree/mage/issues/2215): " . lc($setAbbr) ."_issue_tracker.txt\n");
