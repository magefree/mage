#!/usr/bin/perl -w

#author: North

use Text::Template;
use strict;


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
    $string =~ s/[-,\s\']//g;
    $string;
}

sub fixCost {
    my $string = $_[0];
    $string =~ s/{([2BUGRW])([2BUGRW])}/{$1\/$2}/g;
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

open (DATA, $keywordsFile) || die "can't open $keywordsFile";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $keywords{toCamelCase($data[0])}= $data[1];
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
my $cardName = $ARGV[0];
if(!$cardName) {
    print 'Enter a card name: ';
    $cardName = <STDIN>;
    chomp $cardName;
}

if (!exists $cards{$cardName}) {
    die "Card name doesn't exist: $cardName\n";
}

# Check if card is already implemented
foreach my $setName (keys %{$cards{$cardName}}) {
	if (exists $knownSets{$setName}) {
        my $fileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$setName} . "/" . toCamelCase($cardName) . ".java";
        if(-e $fileName) {
            die "$cardName is already implemented (set found in: $setName).\n";
        }
    }
}

# Generate the cards
my $simpleOnly = $ARGV[1] || 'false';
my $template = Text::Template->new(TYPE => 'FILE', SOURCE => 'cardClass.tmpl', DELIMITERS => [ '[=', '=]' ]);
my $templateExtended = Text::Template->new(TYPE => 'FILE', SOURCE => 'cardExtendedClass.tmpl', DELIMITERS => [ '[=', '=]' ]);
my %vars;

$vars{'author'} = $author;
$vars{'name'} = $cardName;
$vars{'className'} = toCamelCase($cardName);

if ($simpleOnly ne 'true') {
    print "Files generated:\n";
}
my $baseRarity = '';
foreach my $setName (keys %{$cards{$cardName}}) {
	if (exists $knownSets{$setName}) {
        my $fileName = "../Mage.Sets/src/mage/sets/" . $knownSets{$setName} . "/" . toCamelCase($cardName) . ".java";
        my $result;

        $vars{'set'} = $knownSets{$setName};
        $vars{'expansionSetCode'} = $sets{$setName};
        $vars{'cardNumber'} = $cards{$cardName}{$setName}[2];
        $vars{'rarity'} = $raritiesConversion{$cards{$cardName}{$setName}[3]};

        if (!$baseRarity) {
            $baseRarity = $cards{$cardName}{$setName}[3];
            
            $vars{'manaCost'} = fixCost($cards{$cardName}{$setName}[4]);
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
                        $vars{'subType'} .= "\n        this.subtype.add(\"$1\");";
                    } else {
                        $vars{'subType'} .= "\n        this.supertype.add(\"$1\");";
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
            if ($vars{'colors'} || $vars{'power'}) {
                $vars{'colors'} = "\n" . $vars{'colors'};
            }
            
            $vars{'abilitiesImports'} = '';
            $vars{'abilities'} = '';

            my @abilities = split('\$', $cards{$cardName}{$setName}[8]);
            foreach my $ability (@abilities) {
                $ability =~ s/ <i>.+?<\/i>//g;

                my $notKeyWord;
                foreach my $keyword (keys %keywords) {
                    if (toCamelCase($ability) =~ m/^$keyword(?=[A-Z{\d]|$)/g) {
                        $notKeyWord = 'false';
                        my @ka = split(', ', $ability);
                        foreach my $kw (@ka) {
                            my $kwUnchanged = $kw;
                            foreach my $kk (keys %keywords) {
                                if (toCamelCase($kw) =~ m/^$kk(?=[A-Z{\d]|$)/g) {
                                    $kw = $kk;
                                }
                            }
                            if ($keywords{$kw}) {
                                if ($keywords{$kw} eq 'instance') {
                                    $vars{'abilities'} .= "\n        this.addAbility(" . $kw . "Ability.getInstance());";
                                } elsif ($keywords{$kw} eq 'new') {
                                    $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . "Ability());";
                                } elsif ($keywords{$kw} eq 'number') {
                                    $ability =~ m/(\b\d+?\b)/g;
                                    $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(' . $1 . '));';
                                } elsif ($keywords{$kw} eq 'cost') {
                                    $ability =~ m/({.*})/g;
                                    $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(new ManaCostsImpl("' . fixCost($1) . '")));';
                                    $vars{'abilitiesImports'} .= "\nimport mage.abilities.costs.mana.ManaCostsImpl;";
                                }
                                
                                $vars{'abilitiesImports'} .= "\nimport mage.abilities.keyword." . $kw . "Ability;";
                            } else {
                                $vars{'abilities'} .= "\n        // $kwUnchanged";
                                if ($simpleOnly eq 'true') {
                                    exit 0;
                                }
                            }
                        }
                    }
                }
                
                if (!$notKeyWord) {
                    $vars{'abilities'} .= "\n        // $ability";
                    if ($simpleOnly eq 'true') {
                        exit 0;
                    }
                }
            }
            if ($vars{'abilities'}) {
                $vars{'abilities'} = "\n" . $vars{'abilities'};
            }

            $vars{'baseSet'} = $vars{'set'};
            $vars{'baseClassName'} = $vars{'className'};

            $result = $template->fill_in(HASH => \%vars);
        } else {
            $vars{'rarityExtended'} = '';
            if ($baseRarity ne $cards{$cardName}{$setName}[3]) {
                $vars{'rarityExtended'} = "\n        this.rarity = Rarity.$raritiesConversion{$cards{$cardName}{$setName}[3]};";
            }
            $result = $templateExtended->fill_in(HASH => \%vars);
        }

        open CARD, "> $fileName";
        print CARD $result;
        close CARD;

        print "$vars{'set'}.$vars{'className'}\n";
	}
}
