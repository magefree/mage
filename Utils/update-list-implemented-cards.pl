#!/usr/bin/perl -w

#author: North

use strict;

my $dataFile = "mtg-cards-data.txt";
my $knownSetsFile = "known-sets.txt";
my $oldListFile = "oldList.txt";
my $newListFile = "newList.txt";


my %cardsBySet;
my %knownSets;
my %oldList;


sub getClassName {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    $string;
}

open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $cardsBySet{$data[1]}{$data[0]} = \@data;
}
close(DATA);


open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[0]}= $data[1];
}
close(DATA);


open (DATA, $oldListFile) || die "can't open $oldListFile";
while(my $line = <DATA>) {
    chomp $line;
    $oldList{$line} = 1;
}
close(DATA);


# Logic starts here

my %implementedCards;

foreach my $setName (keys %knownSets) {
    foreach my $keyCard (keys %{$cardsBySet{$setName}}) {
        my $fileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$setName} . "/" . getClassName($keyCard) . ".java";
        if(-e $fileName) {
            $implementedCards{$keyCard} = 1;
        }
    }
}

open (OLD, "> $oldListFile");
open (NEW, "> newList.txt");
foreach my $cardName (sort (keys %implementedCards)) {
    print OLD $cardName . "\n";
    if (!exists $oldList{$cardName}) {
        print NEW $cardName . "\n";
    }
}
close (OLD);
close (NEW);