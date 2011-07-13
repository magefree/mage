#!/usr/bin/perl -w

use Text::Template;
use strict;


my $authorFile = 'author.txt';
my $dataFile = "mtg-cards-data.txt";
my $setsFile = "mtg-sets-data.txt";
my $knownSetsFile = "known-sets.txt";

my %cards;
my %sets;
my %knownSets;

my @setCards;

print "Enter a set name: ";
my $setName = <STDIN>;
chomp $setName;

my $template = Text::Template->new(SOURCE => 'cardExtendedClass.tmpl', DELIMITERS => [ '[=', '=]' ]);

sub getClassName {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\']//g;
    $string;
}

my $author;
if (-e $authorFile) {
    open (DATA, $authorFile);
    $author = <DATA>;
    close(DATA);
} else {
    $author = 'anonymous';
}

open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $cards{$data[0]}{$data[1]} = \@data;
    
    if ($data[1] eq $setName) {
        push(@setCards, $data[0]);
    }
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
    $knownSets{$data[0]}= $data[1];
}
close(DATA);

# Generate the cards

my %vars;
$vars{'author'} = $author;
$vars{'set'} = $knownSets{$setName};
$vars{'expansionSetCode'} = $sets{$setName};

print "Files generated:\n";
foreach my $cardName (@setCards) {
    my $className = getClassName($cardName);
    my $currentFileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$setName} . "/" . $className . ".java";
    
    if(! -e $currentFileName) {
        $vars{'className'} = $className;
        $vars{'cardNumber'} = $cards{$cardName}{$setName}[2];
        
        my $found = 0;
        foreach my $key (keys %{$cards{$cardName}}) {
            if (exists $knownSets{$key} && $found eq 0) {
                my $fileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$key} . "/" . $className . ".java";
                if(-e $fileName) {
                    open (DATA, $fileName);
                    while(my $line = <DATA>) {
                        if ($line =~ /extends CardImpl<(\w+?)>/) {
                            $vars{'baseClassName'} = $1;
                            $vars{'baseSet'} = $knownSets{$key};
                            $found = 1;
                        }
                    }
                    close(DATA);
                }
            }
        }
        
        if($found eq 1) {
            my $result = $template->fill_in(HASH => \%vars);
            if (defined($result)) {
                print $vars{'set'} . "." . $vars{'className'} . "\n";
                open CARD, "> $currentFileName";
                print CARD $result; 
                close CARD;
            }
        }
    }
}
