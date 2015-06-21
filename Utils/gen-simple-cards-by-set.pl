#!/usr/bin/perl -w

#author: North

use strict;


my $dataFile = 'mtg-cards-data.txt';
my $knownSetsFile = "known-sets.txt";

my %knownSets;
my @setCards;

sub toCamelCase {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    $string;
}

print 'Enter a set name: ';
my $setName = <STDIN>;
chomp $setName;


open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    if ($data[1] eq $setName) {
        push(@setCards, \@data);
    }
}
close(DATA);

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[0]}= $data[1];
}
close(DATA);

if(!exists $knownSets{$setName}) {
    die "You must add the set to known-sets.txt\n";
}

my $packageName = $knownSets{$setName};

$setName =~ s/"/\\"/g;
system("gen-existing-cards-by-set.pl \"$setName\"");

# Generate missing simple cards
print "Simple cards generated: \n";
foreach my $cardName (@setCards) {
    my $fileName = "../Mage.Sets/src/mage/sets/" . $packageName . "/" . toCamelCase(${$cardName}[0]) . ".java";
    if(!-e $fileName) {
        system("gen-card.pl \"${$cardName}[0]\" true");
    }
}