#!/usr/bin/perl -w

use Switch;
use strict;

my $source = "zendikar-pick.htm";
my $destination = "result.txt";

open SRC, "< $source" or die "Can't open source: $!\n";
open DST, "> $destination" or die "Can't open destination: $!\n";

my $state = 0;

my $name;
my $rate;
my $max;
my $med;
my $min;

while (<SRC>) {
	my $s = $_;
	chomp $s;
	if ($state == 0) {
		if ($s eq "<table width=540 cellpadding=1 cellspacing=0 border=0 align=center>") {
			$state = 1;				
			next;
		}
	} elsif ($state == 1) {
		if ($s =~/<tr>/) {
			$state = 2;			
			next;
		}
	} elsif ($state == 2) {		
		if ($s =~/>([\w ',]+)<\/a>/) {
			$name = $1;
			$state = 3;		
			next;
		}
	} elsif ($state == 3) {
		if ($s =~/<td /) {
			$state = 4;
			next;
		}
	} elsif ($state == 4) {				
		$state = 5;
		next;
    } elsif ($state == 5) {				
		$state = 6;
		next;
    } elsif ($state == 6) {		
		$state = 7;
		next;
    } elsif ($state == 7) {
		if ($s =~/^\s+([0-9]*\.?[0-9]+)/) {
			$rate = $1;
			$state = 8;		
			next;
		}
	} elsif ($state == 8) {
		if ($s =~/\$([0-9]*\.?[0-9]+)/) {			
			$max = $1;
			$state = 9;
		}
	} elsif ($state == 9) {
		if ($s =~/\$([0-9]*\.?[0-9]+)/) {
			$med = $1;
			$state = 10;
		}
	} elsif ($state == 10) {
		if ($s =~/\$([0-9]*\.?[0-9]+)/) {
			$min = $1;
			$state = 11;			
		}
	} elsif ($state == 11) {
		print "$name|$rate|$med\n";
		$state = 1;
	}	
}