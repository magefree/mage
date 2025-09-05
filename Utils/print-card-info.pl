#!/usr/bin/perl -w

#author: Jmlundeen

use Text::Template;
use strict;
use utf8;
use open ':std', ':encoding(UTF-8)';

my $dataFile = 'mtg-cards-data.txt';
my $setsFile = 'mtg-sets-data.txt';
my $cardInfoTemplate = 'cardInfo.tmpl';

my %cards;
my %sets;

sub toCamelCase {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\':.!\/]//g;
    $string;
}

sub printCardInfo {
    my ($cardName, $infoTemplate) = @_;

    if (!exists $cards{$cardName}) {
        print "Card name doesn't exist: $cardName\n\n";
        return;
    }

    my %vars;
    $vars{'classNameLower'} = lcfirst(toCamelCase($cardName));
    my @card;

    foreach my $setName (keys %{$cards{$cardName}}) {
        @card = @{(values(%{$cards{$cardName}{$setName}}))[0]};
        last; # Just get the first one
    }

    $vars{'cardName'} = $card[0];
    $vars{'manaCost'} = $card[4];
    $vars{'typeLine'} = $card[5];

    # Combine power and toughness if they exist
    my $powerToughness = "";
    if ($card[6] && $card[7] && $card[6] ne "" && $card[7] ne "") {
        $powerToughness = "$card[6]/$card[7]";
    }
    $vars{'powerToughness'} = $powerToughness;

    my $cardAbilities = $card[8];
    my @abilities = split(/\$/, $cardAbilities);
    my $abilitiesFormatted = join("\n    ", @abilities);
    $vars{'abilities'} = $abilitiesFormatted;

    my $result = $infoTemplate->fill_in(HASH => \%vars);
    print "$result\n\n";
}

# Load data files
open(DATA, $dataFile) || die "can't open $dataFile : $!";
while (my $line = <DATA>) {
    my @data = split('\\|', $line);
    $cards{$data[0]}{$data[1]}{$data[2]} = \@data;
}
close(DATA);

open(DATA, $setsFile) || die "can't open $setsFile : $!";
while (my $line = <DATA>) {
    my @data = split('\\|', $line);
    $sets{$data[0]} = $data[1];
}
close(DATA);

# Get card names from arguments
my @cardNames = @ARGV;
if (@cardNames == 0) {
    print 'Enter card names (one per line, empty line to finish): ';
    while (my $input = <STDIN>) {
        chomp $input;
        last if $input eq '';
        push @cardNames, $input;
    }
}

if (@cardNames == 0) {
    die "No card names provided.\n";
}

# Load template
my $infoTemplate = Text::Template->new(TYPE => 'FILE', SOURCE => $cardInfoTemplate, DELIMITERS => [ '[=', '=]' ]);

# Print card info for each card
foreach my $cardName (@cardNames) {
    printCardInfo($cardName, $infoTemplate);
}