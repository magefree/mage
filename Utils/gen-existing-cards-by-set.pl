#!/usr/bin/perl -w

use Text::Template;
use strict;

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
    $string =~ s/\b(\w+)\b/ucfirst($1)/ge;
    $string =~ s/[, ]//g;
    $string;
}

sub getOldClassName {
    my $string = $_[0];
    $string =~ s/[, ]//g;
    $string;
}

open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    my @cardData = split('\\|', $line);
    
    $cards{$cardData[1]}{$cardData[2]} = \@cardData;
    
    if ($cardData[2] eq $setName) {
        push(@setCards, $cardData[1]);
    }
}

close(DATA);

open (DATA, $setsFile) || die "can't open $setsFile";
while(my $line = <DATA>) {
    my @setData = split('\\|', $line);
    $sets{$setData[0]}= \@setData;
}
close(DATA);

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    my @setData = split('\\|', $line);
    $knownSets{$setData[0]}= $setData[2];
}
close(DATA);


my %vars;
$vars{'set'} = $knownSets{$setName};
$vars{'expansionSetCode'} = $sets{$setName}[1];

foreach my $cardName (@setCards) {
    my $className = getClassName($cardName);
    my $currentFileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$setName} . "/" . $className . ".java";
    
    if(! -e $currentFileName) {
        $vars{'className'} = $className;
        $vars{'cardNumber'} = $cards{$cardName}{$setName}[5];
        
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
            #print $vars{'set'} . "|" . $vars{'baseSet'} . "|" . $vars{'className'} . "|" . $vars{'baseClassName'} . "|" . $vars{'expansionSetCode'} . "\n";
            my $result = $template->fill_in(HASH => \%vars);
            if (defined($result)) {
                #print $result;
                open CARD, "> $currentFileName";
                print CARD $result; 
                close CARD;
            }
        }
    }
}