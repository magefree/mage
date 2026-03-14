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

# Resolve a user-provided card name to the canonical card key in %cards.
# Tries:
# 1) exact key
# 2) case-insensitive exact match
# 3) case-insensitive substring match (if single match => use it, if multiple => warn and return undef)
sub resolveCardName {
    my ($input) = @_;
    return undef unless defined $input;
    # trim whitespace
    $input =~ s/^\s+|\s+$//g;
    return $input if exists $cards{$input};

    my $lc_input = lc $input;

    # case-insensitive exact
    foreach my $k (keys %cards) {
        return $k if lc($k) eq $lc_input;
    }

    # substring (partial) matches
    my @matches = grep { index(lc($_), $lc_input) != -1 } keys %cards;
    if (@matches == 1) {
        return $matches[0];
    } elsif (@matches > 1) {
        @matches = sort @matches;
        # If not interactive, don't block; print candidates and return undef
        unless (-t STDIN) {
            warn "Multiple matches found for '$input' (non-interactive):\n";
            foreach my $m (@matches) { warn "  $m\n"; }
            warn "Please be more specific.\n";
            return undef;
        }

        print "Multiple matches found for '$input':\n";
        my $i = 0;
        foreach my $m (@matches) {
            $i++;
            print "  $i) $m\n";
        }

        while (1) {
            print "Select a number (1-$i) or 0 to cancel: ";
            my $choice = <STDIN>;
            unless (defined $choice) { print "\nNo selection (EOF). Skipping.\n"; return undef; }
            chomp $choice;
            $choice =~ s/^\s+|\s+$//g;

            # numeric choice
            if ($choice =~ /^\d+$/) {
                my $num = int($choice);
                if ($num == 0) {
                    return undef;
                } elsif ($num >= 1 && $num <= $i) {
                    return $matches[$num - 1];
                }
            } else {
                # try exact name match among candidates (case-insensitive)
                foreach my $m (@matches) {
                    return $m if lc($m) eq lc($choice);
                }
            }

            print "Invalid selection, please try again.\n";
        }
    }

    return undef;
}


sub generateCardInfo {
    my ($cardName, $infoTemplate) = @_;

    # attempt to resolve loosely if direct lookup fails
    if (!exists $cards{$cardName}) {
        my $resolved = resolveCardName($cardName);
        if (!defined $resolved) {
            warn "Card name doesn't exist: $cardName (skipping)\n";
            return "";
        }
        $cardName = $resolved;
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

    # Check if this is a planeswalker
    my $isPlaneswalker = $card[5] =~ /Planeswalker/i;

    my $cardAbilities;
    if ($isPlaneswalker) {
        # For planeswalkers: field 6 is loyalty, field 7 is abilities
        $vars{'loyalty'} = $card[6] if $card[6];  # loyalty
        $cardAbilities = $card[7];
    } else {
        # For non-planeswalkers: field 6/7 is power/toughness, field 8 is abilities
        if ($card[6]) {
            $vars{'powerToughness'} = "$card[6]/$card[7]";
        }
        $cardAbilities = $card[8];
    }

    my @abilities = split(/\$/, $cardAbilities);
    my $abilitiesFormatted = join("\n    ", @abilities);
    $vars{'abilities'} = $abilitiesFormatted;

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

# Get card names from arguments or prompt
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

# Trim whitespace for all inputs
foreach my $i (0..$#cardNames) {
    $cardNames[$i] =~ s/^\s+|\s+$//g if defined $cardNames[$i];
}

# Main card is the first one
my $mainCardNameInput = $cardNames[0];

# Resolve main card with loose matching
my $resolvedMain = resolveCardName($mainCardNameInput);
if (!defined $resolvedMain) {
    die "Card name doesn't exist or is ambiguous: $mainCardNameInput\n";
}
my $mainCardName = $resolvedMain;

my @additionalCardsInput = ();
if (@cardNames > 1) {
    @additionalCardsInput = @cardNames[1..$#cardNames];
}

if (!exists $cards{$mainCardName}) {
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
    if (exists $sets{$setName}) {
        $setCode = lc($sets{$setName});
        last;  # Use the first valid set found
    }
}

# Fallback if no valid set code was found
unless (defined $setCode) {
    warn "Warning: No valid set code found for card '$mainCardName'. Using 'unk' as fallback.\n";
    warn "Available sets for this card: " . join(", ", keys %{$cards{$originalName}}) . "\n";
    $setCode = 'unk';
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

# Generate additional card info templates (resolve each loosely)
foreach my $additionalCardInput (@additionalCardsInput) {
    my $resolved = resolveCardName($additionalCardInput);
    if (!defined $resolved) {
        warn "Skipping additional card (not found or ambiguous): $additionalCardInput\n";
        next;
    }
    my $additionalInfo = generateCardInfo($resolved, $infoTemplate);
    if (defined $additionalInfo && $additionalInfo ne '') {
        $allCardInfo .= "\n\n" . $additionalInfo;
    }
}

$vars{'cardInfo'} = $allCardInfo;
$result = $template->fill_in(HASH => \%vars);

open CARD, "> $fileName";
print CARD $result;
close CARD;

print "$fileName\n";
if (@additionalCardsInput > 0) {
    print "Additional cards included: " . join(", ", map { resolveCardName($_) // $_ } @additionalCardsInput) . "\n";
}