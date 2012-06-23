#!/usr/bin/perl -w

use strict;

my $dataFile = 'mtg-cards-data.txt';
my $setsFile = 'mtg-sets-data.txt';
my $knownSetsFile = 'known-sets.txt';

my $cards_count = 0;

my %knownSets;
my %sets;
my %cardsBySet;
my %cards;

sub toCamelCase {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    $string;
}

open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $cardsBySet{$data[1]}{toCamelCase($data[0])} = \@data;
}
close(DATA);

open (DATA, $setsFile) || die "can't open $setsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $sets{$data[0]}= $data[1];
}
close(DATA);

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[1]}= $data[0];
}
close(DATA);


open CARDS, "< added_cards.txt" or die;
while (<CARDS>) {
    my $line = $_;
    if ( $line =~/A Mage.Sets\\src\\mage\\sets\\(\w.*)\\(\w.*)\.java/ ) {
        $cards_count++;
        my $set = $1;
        my $card = $2;
        if (!exists($cards{$set})) {
            $cards{$set} = [];
        }

        push @{$cards{$set}}, $card;
    }
}

open REPORT, "> added_cards_in_wiki_format.txt";
print REPORT " * Added cards ($cards_count):\n";
foreach my $set (keys(%cards)) {
    if ($set ne "tokens") {
        if (exists $knownSets{$set}) {
            print REPORT "   * $sets{$knownSets{$set}}: ";
        } else {
            print REPORT " $set: ";
        }
        my $first = 1;
        foreach my $card (@{$cards{$set}}) {
            if ($first == 0) {
                print REPORT "; ";
            } else {
                $first = 0;
            }
            if ($cardsBySet{$knownSets{$set}}{$card}) {
                print REPORT $cardsBySet{$knownSets{$set}}{$card}[0];
            } else {
                #$card =~ s/([A-Z]{1}[a-z]+)/ $1/g;
                #$card =~ s/A Ether/ AEther/g;
                print "not processed: $sets{$knownSets{$set}} $card\n";
            }
        }
        print REPORT "\n";
    }
}

close REPORT;