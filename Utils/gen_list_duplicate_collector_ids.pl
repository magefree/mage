#!/usr/bin/perl -w

#author: spjspj

use strict;
use Scalar::Util qw(looks_like_number);

my $dataFile = "mtg-cards-data.txt";
my $setsFile = "mtg-sets-data.txt";
my $knownSetsFile = "known-sets.txt";

my %sets;
my %knownSets;

my @setCards;
my %nameSetNumber;
my %setNumber;

open (DATA, $dataFile) || die "can't open $dataFile";
print ("Looking at data in $dataFile\n");
while (<DATA>)
{
    chomp;
    my $line = $_;

    my $addDay = "";
    if ($line =~ m/(transform|transformed under)/img)
    {
        $addDay = "-day";
    }
    if ($line =~ m/(meld them|melds with)/img)
    {
        $addDay = "-day";
    }

    $line =~ s/^(([^\|]+)\|([^\|]+)\|([^\|]+))\|.*/$1/;
    my $name = $2;
    my $set = $3;
    my $number = $4;


    $nameSetNumber {$line . $addDay} ++;
    my $sn = "$set.$number$addDay";
    if (!defined ($setNumber {$sn}))
    {
        $setNumber {$sn} = $name;
    } 
    elsif ($setNumber {$sn} ne $name)
    {
        print ("Problem -> '$name' has the same collectorID/Set Name as ::: $setNumber{$sn} ($sn)\n");
    }
}
close(DATA);
print ("Finished with data in $dataFile\n");

my $key;
foreach $key (sort keys (%nameSetNumber))
{
    if ($nameSetNumber {$key} > 1)
    {
        #print ("$key >> $nameSetNumber{$key} may be a duplicate!\n"); 
    }
}
