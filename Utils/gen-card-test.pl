#!/usr/bin/perl -w

#author: North/Jmlundeen
=begin comment

To use this script you can call it with perl ./gen-card-test.pl "Storm Crow" "Lightning Bolt"
The first argument (Storm Crow) is the main card to generate a test class for.
The cards after (Lighting Bolt) will place card info and create a variable inside the test class.
You can add as many additional cards as you like.

You can also call the script without arguments and it will prompt you for card names
=cut


use Text::Template;
use strict;
use File::Path qw(make_path);

my $authorFile = 'author.txt';
my $dataFile = 'mtg-cards-data.txt';
my $setsFile = 'mtg-sets-data.txt';
my $knownSetsFile = 'known-sets.txt';
my $keywordsFile = 'keywords.txt';

my %cards;
my %sets;
my %knownSets;
my %keywords;

sub toCamelCase {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\':.!\/]//g;
    $string;
}

sub fixCost {
    my $string = $_[0];
    $string =~ s/{([2BUGRW])([2BUGRW])}/{$1\/$2}/g;
    $string;
}

sub generateCardInfo {
    my ($cardName, $infoTemplate) = @_;

    if (!exists $cards{$cardName}) {
        warn "Card name doesn't exist: $cardName (skipping)\n";
        return "";
    }

    my %vars;
    $vars{'classNameLower'} = lcfirst(toCamelCase($cardName));
    my @card;

    foreach my $setName (keys %{$cards{$cardName}}) {
        @card = @{(values(%{$cards{$cardName}{$setName}}))[0]};
        last; # Just get the first one for additional cards
    }

    $vars{'cardName'} = $card[0];
    $vars{'manaCost'} = $card[4];
    $vars{'typeLine'} = $card[5];

    my $cardAbilities = $card[8];
    my @abilities = split(/\$/, $cardAbilities);
    my $abilitiesFormatted = join("\n    ", @abilities);
    $vars{'abilities'} = $abilitiesFormatted;
    if ($card[6]) {
        $vars{'powerToughness'} = "$card[6]/$card[7]"
    }

    return $infoTemplate->fill_in(HASH => \%vars);
}

my $author;
if (-e $authorFile) {
    open(DATA, $authorFile) || die "can't open $authorFile : $!";
    $author = <DATA>;
    chomp $author;
    close(DATA);
} else {
    $author = 'anonymous';
}

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

open(DATA, $knownSetsFile) || die "can't open $knownSetsFile : $!";
while (my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[0]} = $data[1];
}
close(DATA);

open(DATA, $keywordsFile) || die "can't open $keywordsFile : $!";
while (my $line = <DATA>) {
    my @data = split('\\|', $line);
    $keywords{toCamelCase($data[0])} = $data[1];
}
close(DATA);

# Get card names from arguments
my @cardNames = @ARGV;
if (@cardNames == 0) {
    print 'Enter a card name: ';
    my $input = <STDIN>;
    chomp $input;
    push @cardNames, $input;

    # Prompt for additional cards
    print 'Enter additional card names (one per line, empty line to finish): ';
    while (my $additionalCard = <STDIN>) {
        chomp $additionalCard;
        last if $additionalCard eq '';  # Empty line ends input
        push @cardNames, $additionalCard;
    }
}

# Main card is the first one
my $mainCardName = $cardNames[0];
my @additionalCards = @cardNames[1..$#cardNames];

if (!exists $cards{$mainCardName}) {
    my $possible;
    foreach $possible (sort keys(%cards)) {
        if ($possible =~ m/$mainCardName/img && $mainCardName =~ m/..../) {
            print("Did you mean $possible?\n");
        }
    }
    die "Card name doesn't exist: $mainCardName\n";
}

my $cardTemplate = 'cardTest.tmpl';
my $cardInfoTemplate = 'cardInfo.tmpl';
my $originalName = $mainCardName;
my $setCode;

# Generate lines to corresponding sets
my %vars;
$vars{'className'} = toCamelCase($mainCardName);
$vars{'classNameLower'} = lcfirst(toCamelCase($mainCardName));
$vars{'cardNameFirstLetter'} = lc substr($mainCardName, 0, 1);

foreach my $setName (keys %{$cards{$originalName}}) {
    $setCode = lc($sets{$setName});
}

# Check if card is already implemented
my $fileName = "../Mage.Tests/src/test/java/org/mage/test/cards/single/" . $setCode . "/" . toCamelCase($mainCardName) . "Test.java";
if (-e $fileName) {
    die "$mainCardName is already implemented.\n$fileName\n";
}

# Create directory if it doesn't exist
my $dir = "../Mage.Tests/src/test/java/org/mage/test/cards/single/" . $setCode;
make_path($dir) unless -d $dir;

# Generate the card templates
my $result;
my $template = Text::Template->new(TYPE => 'FILE', SOURCE => $cardTemplate, DELIMITERS => [ '[=', '=]' ]);
my $infoTemplate = Text::Template->new(TYPE => 'FILE', SOURCE => $cardInfoTemplate, DELIMITERS => [ '[=', '=]' ]);

$vars{'author'} = $author;
$vars{'setCode'} = $setCode;

# Generate main card info
my $allCardInfo = generateCardInfo($mainCardName, $infoTemplate);

# Generate additional card info templates
foreach my $additionalCard (@additionalCards) {
    my $additionalInfo = generateCardInfo($additionalCard, $infoTemplate);
    if (defined $additionalInfo) {
        $allCardInfo .= "\n\n" . $additionalInfo;
    }
}

$vars{'cardInfo'} = $allCardInfo;
$result = $template->fill_in(HASH => \%vars);

open CARD, "> $fileName";
print CARD $result;
close CARD;

print "$fileName\n";
if (@additionalCards > 0) {
    print "Additional cards included: " . join(", ", @additionalCards) . "\n";
}