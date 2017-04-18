#!/usr/bin/perl -w

##
#  File: gen_types_list.pl
#  Author: spjspj
#  Purpose: To open all card java files and count all subtypes/supertypes
#  Purpose: Ones with unique spellings are possibly incorrect!
##

use strict;
use Scalar::Util qw(looks_like_number);

my $dir_listing = "dir \/a \/b \/s ..\\Mage.Sets\\ | find \".java\" |";

open (DIR_LISTING, "$dir_listing");
my %types;
my %types_files;

while (<DIR_LISTING>)
{
    chomp;
    my $file = $_;

    my $name = "";
    my $cardNum = "";

    open (JAVA_FILE, "$file");

    while (<JAVA_FILE>)
    {
        chomp;
        
        # Eg: this.subtype.add("Human");
        my $line = $_;
        if ($line =~ m/this.subtype.add.*"([^"]*)".;/)
        {
            $types{$1}++;
            $types_files{$1} .= $file . ",,,";
        }
        if ($line =~ m/addSuperType.*"([^"]*)"/)
        {
            $types{$1}++;
            $types_files{$1} .= $file . ",,,";
        }
   }

    close (JAVA_FILE);
}
my $key;
foreach $key (sort keys (%types))
{
    print ("$types{$key} = $key  .... ");
    if ($types{$key} < 10)
    {
        print ("    In files:$types_files{$key}");
    }
    print ("\n");
}
