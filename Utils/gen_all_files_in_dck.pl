#!/usr/bin/perl -w

##
#  File: gen_all_files_in_dck.pl
#  Author: spjspj
#  Purpose: Print all cards in .dck format
##

use strict;
use Scalar::Util qw(looks_like_number);
use File::Find;
use File::Spec;

sub toCamelCase
{
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    return $string;
}

my $option = $ARGV[0];
if (!defined $option || $option eq "")
{
    die "Usage: $0 <pattern>\n";
}

my $sets_dir = File::Spec->catdir('..', 'Mage.Sets', 'src', 'mage', 'sets');
my @java_files;
find(
    {
        no_chdir => 1,
        wanted   => sub {
            return unless -f $_;
            return unless /\.java\z/;
            push @java_files, $File::Find::name;
        },
    },
    $sets_dir
);

my %setsForJavafile;
my $totalCards = 0;

foreach my $file (@java_files)
{
    my $name = "";
    my $cardNum = "";

    open (JAVA_FILE, "$file");
    my $fileKey = $file;
    $fileKey =~ s/^.*[\/\\]//;
    if ($file !~ m/\.java$/)
    {
        next;
    }

    while (<JAVA_FILE>)
    {
        chomp;

        # Eg: Card Name
        #cards.add(new SetCardInfo("Bonds of Quicksilver", 102, Rarity.COMMON, mage.cards.b.BondsOfQuicksilver.class));
        my $line = $_;

        # Eg: Set Trigraph
        #super("Commander 2016", "C16", ExpansionSet.buildDate(2016, 11, 11), SetType.SUPPLEMENTAL);
        $line =~ s/\\\"/'/img;
        if ($line =~ m/super\("[^"]*?", "([^"]*?)", ExpansionSet.buildDate.*/img)
        {
            my $trigraph = $1;
            my $f = $fileKey;
            $f =~ s/ Edition//;
            $f =~ s/\.java//;
            $f = toCamelCase($f);
            $setsForJavafile {$f} = $trigraph;
        }

        if ($line !~ m/$option/img)
        {
            next;
        }

        if ($line =~ m/SetCardInfo\("([^"]+)",([^,]+),/im)
        {
            $name = $1;
            $cardNum = $2;
            $cardNum =~ s/[^a-z0-9]//img;

            my $f = $fileKey;
            $f =~ s/ Edition//;
            $f =~ s/\.java//;
            $f = toCamelCase($f);
            $totalCards++;
            if ($cardNum !~ m/\d\d\d\d/)
            {
                print ("1 [$setsForJavafile{$f}:$cardNum] $name\n");
            }
        }
    }

    close (JAVA_FILE);
}
print ("Found a total of $totalCards\n");
