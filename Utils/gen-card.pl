#!/usr/bin/perl -w

use Text::Template;
use strict;


my $authorFile = 'author.txt';
my $dataFile = 'mtg-cards-data.txt';
my $setsFile = 'mtg-sets-data.txt';
my $knownSetsFile = 'known-sets.txt';


my %cards;
my %sets;
my %knownSets;

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

my %cardTypes;
$cardTypes{'Artifact'} = 'CardType.ARTIFACT';
$cardTypes{'Creature'} = 'CardType.CREATURE';
$cardTypes{'Enchantment'} = 'CardType.ENCHANTMENT';
$cardTypes{'Instant'} = 'CardType.INSTANT';
$cardTypes{'Land'} = 'CardType.LAND';
$cardTypes{'Sorcery'} = 'CardType.SORCERY';

my %raritiesConversion;
$raritiesConversion{'C'} = 'COMMON';
$raritiesConversion{'U'} = 'UNCOMMON';
$raritiesConversion{'R'} = 'RARE';
$raritiesConversion{'M'} = 'MYTHIC';

my %manaToColor;
$manaToColor{'B'} = 'Black';
$manaToColor{'U'} = 'Blue';
$manaToColor{'G'} = 'Green';
$manaToColor{'R'} = 'Red';
$manaToColor{'W'} = 'White';


# Get card name
print 'Enter a card name: ';
my $cardName = <STDIN>;
chomp $cardName;

# Check if card is already implemented
foreach my $setName (keys %{$cards{$cardName}}) {
	if (exists $knownSets{$setName}) {
        my $fileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$setName} . "/" . getClassName($cardName) . ".java";
        if(-e $fileName) {
            die "$cardName is already implemented (set found: $setName).\n";
        }
    }
}

# Generate the cards
my $template = Text::Template->new(SOURCE => 'cardClass.tmpl', DELIMITERS => [ '[=', '=]' ]);
my $templateExtended = Text::Template->new(SOURCE => 'cardExtendedClass.tmpl', DELIMITERS => [ '[=', '=]' ]);
my %vars;

$vars{'author'} = $author;
$vars{'name'} = $cardName;
$vars{'className'} = getClassName($cardName);

print "Files generated:\n";
my $baseSet = '';
foreach my $setName (keys %{$cards{$cardName}}) {
	if (exists $knownSets{$setName}) {
        my $fileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$setName} . "/" . getClassName($cardName) . ".java";
        my $result;
        
        $vars{'set'} = $knownSets{$setName};
        $vars{'expansionSetCode'} = $sets{$setName};
        $vars{'cardNumber'} = $cards{$cardName}{$setName}[2];
        $vars{'rarity'} = $raritiesConversion{$cards{$cardName}{$setName}[3]};
        
        if (!$baseSet) {
            $baseSet = $knownSets{$setName};
            $vars{'manaCost'} = $cards{$cardName}{$setName}[4];
            $vars{'power'} = $cards{$cardName}{$setName}[6];
            $vars{'toughness'} = $cards{$cardName}{$setName}[7];

			my @types;
            $vars{'subType'} = '';
			my $type = $cards{$cardName}{$setName}[5];
			while ($type =~ m/([a-zA-Z]+)( )*/g) {
                if (exists($cardTypes{$1})) {
                    push(@types, $cardTypes{$1});
				} else {
                    if (@types) {
                        $vars{'subType'} .= "        this.subtype.add(\"$1\");\n";
                    } else {
                        $vars{'subType'} .= "        this.supertype.add(\"$1\");\n";
                    }
				}
			}
            $vars{'type'} = join(', ', @types);

            my %colors;
            while ($vars{'manaCost'} =~ m/([BUGRW])/g) {
                $colors{$manaToColor{$1}} = 1;
			}

            $vars{'colors'} = '';
            foreach my $color (keys %colors) {
                $vars{'colors'} .= "\n        this.color.set$color(true);";
            }
            
            $vars{'baseSet'} = $vars{'set'};
            $vars{'baseClassName'} = $vars{'className'};

            $result = $template->fill_in(HASH => \%vars);
        } else {
            $result = $templateExtended->fill_in(HASH => \%vars);
        }
        open CARD, "> $fileName";
        print CARD $result; 
        close CARD;
        
        print "$fileName\n";
	}
}
