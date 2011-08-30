#!/usr/bin/perl -w

use strict;

open CARDS, "< added_cards.txt" or die;

my $cards_count = 0;

my %cards;

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
print REPORT "Added cards ($cards_count):\n";
foreach my $set (keys(%cards)) {
	print REPORT " $set: ";
	foreach my $card (@{$cards{$set}}) {
		print REPORT $card . ", ";
	}
	print REPORT "\n";
}