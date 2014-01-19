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

# gets the set name
my $setName = $ARGV[0];
if(!$setName) {
    print 'Enter a set name: ';
    $setName = <STDIN>;
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

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[0]}= $data[1];
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

my $toPrint = '';
foreach my $card (sort cardSort @setCards) {
	my $className = toCamelCase(@{$card}[0]);
    my $currentFileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$setName} . "/" . $className . ".java";
	if (-e $currentFileName) {
		if ($toPrint) {
			$toPrint .= "\n";
		}   
		$toPrint .= "@{$card}[2]|@{$card}[0]"; 
	}
}
open CARD, "> " . lc($sets{$setName}) . "_implemented.txt";
print CARD $toPrint;
close CARD;

