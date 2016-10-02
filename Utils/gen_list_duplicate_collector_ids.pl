#!/usr/bin/perl -w

##
#  File: gen_list_duplicate_collector_ids.pl
#  Author: spjspj
#  Purpose: To open all card java files and work out if any have duplicate cardNumber / expansionSetCode
##

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

open (DATA, $setsFile) || die "can't open $setsFile";

while(my $line = <DATA>)
{
    my @data = split('\\|', $line);
    $sets{$data[0]}= $data[1];
}
close(DATA);


sub toCamelCase
{
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    $string;
}


open (DATA, $dataFile) || die "can't open $dataFile";
open (FILES_TO_CHECK, ">files_to_check.bat");
my $edit_str = "find \"super(ownerId\" ";
print FILES_TO_CHECK "\@echo  off\n";
print FILES_TO_CHECK "find \"super(ownerId\" ";
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
    my $sn = "$set...$number$addDay";
    if (!defined ($setNumber {$sn}))
    {
        $setNumber {$sn} = $name;
    }
    elsif ($setNumber {$sn} ne $name)
    {
        print ("Problem -> '$name' has the same collectorID/Set Name as ::: $setNumber{$sn} ($sn)\n");
        $sn =~ s/\.\.\..*//;
        my $trigraph = lc($sn);
        $trigraph =~ s/[^a-z]//img;
        print FILES_TO_CHECK " ..\\Mage.Sets\\src\\mage\\sets\\" , $trigraph, "\\", toCamelCase($name), ".java";
        $edit_str .= " ..\\Mage.Sets\\src\\mage\\sets\\" . $trigraph. "\\". toCamelCase($name). ".java ";
    }
}
close(DATA);
print ("Possible problem java files are here: .\\files_to_check.bat\n(\n$edit_str\n)\n");
print FILES_TO_CHECK "\n";
close (FILES_TO_CHECK);

my $dir_listing = "dir \/a \/b \/s ..\\Mage.Sets\\src\\mage\\sets\\ | find \".java\" |";
my %find_info;
my %files_from_key;
open (DIR_LISTING, "$dir_listing");
while (<DIR_LISTING>)
{
    chomp;
    my $file = $_;

    # Example:
    #  super(ownerId, 133, "Witch Hunt", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");
    # or
    #  super(ownerId);
    #  this.cardNumber = "100";
    #  this.expansionSetCode = "C13";
    # or
    #  super(ownerId, 99, "Brine Elemental", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
    #  this.expansionSetCode = "C14";
    my $name = "";
    my $setId = "";
    my $cardNum = "";
    my $class = "";
    my $day = "";

    open (JAVA_FILE, "$file");
    while (<JAVA_FILE>)
    {
        chomp;
        my $line = $_;
        if ($line =~ m/super\(ownerId,([^,]+),([^"]+)?"([^"]+)?"/)
        {
            $name = $3;
            $cardNum = $1;
            $cardNum =~ s/[^a-z0-9]//img;
            $cardNum =~ s/ //img;
        }
        elsif ($line =~ m/expansionSetCode = "([^"]+)"/)
        {
            $setId = $1;
        }
        elsif ($line =~ m/super\(ownerId,([^,]+)/)
        {
            $cardNum = $1;
        }
        elsif ($line =~ m/cardNumber =(.*);/)
        {
            $cardNum = $1;
        }
        elsif ($line =~ m/^public class (.*) extends/)
        {
            $class = $1;
        }
        elsif ($line =~ m/(MeldCondition)/)
        {
            $day = "_day";
        }
        elsif ($line =~ m/(nightCard)/)
        {
            $day = "_night";
        }
    }

    $name =~ s/[^a-z0-9]//img;
    $setId =~ s/[^a-z0-9]//img;
    $cardNum =~ s/[^a-z0-9]//img;
    $class =~ s/[^a-z0-9]//img;
    my $val = "$name($class),$setId,$cardNum.$day";
    my $key = "$setId,$cardNum.$day";
    if (!defined ($find_info {$key}))
    {
        $find_info {$key} = $val;
        $files_from_key {$key} = $file;
    }
    elsif ($key ne ",.")
    {
        print ("$name($class in file:$file) $val\n Has the same key $key as\n$find_info{$key}  val=$val (from key=$key) in file: $files_from_key{$key}\n");
    }

    close (JAVA_FILE);
}
