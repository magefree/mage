#!/usr/bin/perl -w

use strict;

my $knownSetsFile = 'known-sets.txt';

my $cards_count = 0;

my %knownSets;
my %cards;

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[1]}= $data[2];
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
		
		push $cards{$set}, $card;
	}
}

open REPORT, "> added_cards_in_wiki_format.txt";
print REPORT " * Added cards ($cards_count):\n";
foreach my $set (keys(%cards)) {
    if ($set ne "tokens") {
		if (exists $knownSets{$set}) {
			print REPORT "   * $knownSets{$set}: ";
		} else {
			print REPORT " $set: ";
		}
		my $first = 1;
		foreach my $card (@{$cards{$set}}) {
			if ($first == 0) { print REPORT ","; }
			$card =~ s/([A-Z]{1}[a-z]+)/ $1/g;
			$card =~ s/A Ether/ AEther/g;
			print REPORT $card;
			$first = 0;
		}
		print REPORT "\n";
	}
}

close REPORT;