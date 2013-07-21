#!/usr/bin/perl -w

#author: North

use strict;


my $dataFile = "mtg-cards-data.txt";
my $setsFile = "mtg-sets-data.txt";

my %sets;

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

open (DATA, $setsFile) || die "can't open $setsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $sets{$data[0]}= $data[1];
}
close(DATA);


sub cardSort {
  if (@{$a}[2] < @{$b}[2]) { return -1; }
  elsif (@{$a}[2] == @{$b}[2]) { return 0;}
  elsif (@{$a}[2] > @{$b}[2]) { return 1; }
}


my $toPrint = '';
foreach my $card (sort cardSort @setCards) {
    if ($toPrint) {
        $toPrint .= "\n";
    }   
    $toPrint .= "@{$card}[2]|@{$card}[0]"; 
}
open CARD, "> " . lc($sets{$setName}) . ".txt";
print CARD $toPrint;
close CARD;
