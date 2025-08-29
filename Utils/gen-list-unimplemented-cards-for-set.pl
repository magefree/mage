#!/usr/bin/perl -w

#author: North
use Text::Template;
use strict;
use Scalar::Util qw(looks_like_number);

my $dataFile = "mtg-cards-data.txt";
my $setsFile = "mtg-sets-data.txt";
my $knownSetsFile = "known-sets.txt";
my $templateFile = "issue_tracker.tmpl";

my %sets;
my %knownSets;

my @setCards;

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile";
while(my $line = <DATA>) {
    chomp $line;
    my @data = split('\\|', $line);
    $knownSets{$data[0]} = $data[1];
}
close(DATA);

my @basicLands = ("Plains", "Island", "Swamp", "Mountain", "Forest");

# gets the set name
my $setName = $ARGV[0];
if(!$setName) {
    print 'Enter a set name: ';
    $setName = <STDIN>;
    chomp $setName;
}

while (!defined ($knownSets{$setName}))
{
    print ("Invalid set - '$setName'\n");
    print ("  Possible sets you meant:\n");
    my $origSetName = $setName;
    $setName =~ s/^(.).*/$1/;
    my $key;
    foreach $key (sort keys (%knownSets))
    {
        if ($key =~ m/^$setName/img)
        {
            print ("   '$key'\n");
        }
    }

    print 'Enter a set name: ';
    $setName = <STDIN>;
    chomp $setName;
}

open (DATA, $dataFile) || die "can't open $dataFile";
while(my $line = <DATA>) {
    chomp $line;
    my @data = split('\\|', $line);
    if ($data[1] eq $setName) {
        push(@setCards, \@data);
    }
}
close(DATA);

open (DATA, $setsFile) || die "can't open $setsFile";
while(my $line = <DATA>) {
    chomp $line;
    my @data = split('\\|', $line);
    $sets{$data[0]}= $data[1];
}
close(DATA);

sub cardSort {
    if (!looks_like_number(@{$a}[2])) { return -1; }
    if (!looks_like_number(@{$b}[2])) { return 1; }
    if (@{$a}[2] < @{$b}[2]) { return -1; }
    elsif (@{$a}[2] == @{$b}[2]) { return 0;}
    elsif (@{$a}[2] > @{$b}[2]) { return 1; }
}

sub toCamelCase {
    my $string = $_[0];
    $string =~ s/\b([\w']+)\b/ucfirst($1)/ge;
    $string =~ s/[-,\s\'\.!@#*\(\)]//g;
    $string;
}

# Check which cards are implemented
my %cardNames;
my %seenCards;  # Track which card names we've already processed
my @implementedCards;
my @unimplementedCards;
my $previousCollectorNumber = -1;
my %vars;

my $setAbbr = $sets{$setName};

foreach my $card (sort cardSort @setCards) {
    my $className = toCamelCase(@{$card}[0]);
    if ($className ~~ @basicLands) {
        next;
    }

    my $cardName = @{$card}[0];
    my $collectorNumber = @{$card}[2];

    # Skip if we've already processed this card name or is the back face of a card
    if (exists $seenCards{$cardName} or $previousCollectorNumber == $collectorNumber) {
        $seenCards{$cardName} = 1;
        next;
    }
    $seenCards{$cardName} = 1;
    $previousCollectorNumber = $collectorNumber;

    my $currentFileName = "../Mage.Sets/src/mage/cards/" . lc(substr($className, 0, 1)) . "/" . $className . ".java";
    my $cardNameForUrl = $cardName;
    $cardNameForUrl =~ s/ //g;
    my $cardEntry = "- [ ] In progress -- [$cardName](https://scryfall.com/search?q=!\"$cardNameForUrl\"&nbsp;e:$setAbbr)";

    if(-e $currentFileName) {
        # Card is implemented
        $cardNames{$cardName} = 1;
        my $implementedEntry = "- [x] Done -- [$cardName](https://scryfall.com/search?q=!\"$cardNameForUrl\"&nbsp;e:$setAbbr)";
        push(@implementedCards, $implementedEntry);
    } else {
        # Card is not implemented
        $cardNames{$cardName} = 0;
        push(@unimplementedCards, $cardEntry);
    }
}

# Build the unimplemented URL for Scryfall
my $unimplementedUrl = "https://scryfall.com/search?q=";
my @unimplementedNames;
foreach my $cardName (sort keys %cardNames) {
    if ($cardNames{$cardName} == 0) {
        my $urlCardName = $cardName;
        $urlCardName =~ s/ //g;
        $urlCardName =~ s/"/\\"/g;  # Escape quotes
        push(@unimplementedNames, "!\"$urlCardName\"");
    }
}
$unimplementedUrl .= join("or", @unimplementedNames) . "&unique=cards";

# Read template file
my $template = Text::Template->new(TYPE => 'FILE', SOURCE => $templateFile, DELIMITERS => [ '[=', '=]' ]);
$vars{'unimplemented'} = join("\n", @unimplementedCards);
$vars{'implemented'} = join("\n", @implementedCards);
$vars{'setName'} = $setName;
$vars{'unimplementedUrl'} = $unimplementedUrl;
my $result = $template->fill_in(HASH => \%vars);
# Write the final issue tracker file
my $outputFile = lc($sets{$setName}) . "_issue_tracker.txt";
open(OUTPUT, "> $outputFile") || die "can't open $outputFile for writing";
print OUTPUT $result;
close(OUTPUT);

print "Issue tracker generated: $outputFile\n";
print "Implemented cards: " . scalar(@implementedCards) . "\n";
print "Unimplemented cards: " . scalar(@unimplementedCards) . "\n";