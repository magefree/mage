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
    $string =~ s/[-,\s\':.!]//g;
    $string;
}

sub fixCost {
    my $string = $_[0];
    $string =~ s/{([2BUGRW])([2BUGRW])}/{$1\/$2}/g;
    $string;
}

my $author;
if (-e $authorFile) {
    open (DATA, $authorFile) || die "can't open $authorFile : $!";
    $author = <DATA>;
    chomp $author;
    close(DATA);
} else {
    $author = 'anonymous';
}

open (DATA, $dataFile) || die "can't open $dataFile : $!";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $cards{$data[0]}{$data[1]} = \@data;
}
close(DATA);

open (DATA, $setsFile) || die "can't open $setsFile : $!";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $sets{$data[0]}= $data[1];
    #print "$data[0]--$data[1]\n"
}
close(DATA);

open (DATA, $knownSetsFile) || die "can't open $knownSetsFile : $!";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $knownSets{$data[0]}= $data[1];
}
close(DATA);

open (DATA, $keywordsFile) || die "can't open $keywordsFile : $!";
while(my $line = <DATA>) {
    my @data = split('\\|', $line);
    $keywords{toCamelCase($data[0])}= $data[1];
}
close(DATA);

my %cardTypes;
$cardTypes{'Artifact'} = 'CardType.ARTIFACT';
$cardTypes{'Conspiracy'} = 'CardType.CONSPIRACY';
$cardTypes{'Creature'} = 'CardType.CREATURE';
$cardTypes{'Enchantment'} = 'CardType.ENCHANTMENT';
$cardTypes{'Instant'} = 'CardType.INSTANT';
$cardTypes{'Land'} = 'CardType.LAND';
$cardTypes{'Sorcery'} = 'CardType.SORCERY';
$cardTypes{'Planeswalker'} = 'CardType.PLANESWALKER';
$cardTypes{'Tribal'} = 'CardType.TRIBAL';

my %raritiesConversion;
$raritiesConversion{'C'} = 'COMMON';
$raritiesConversion{'U'} = 'UNCOMMON';
$raritiesConversion{'R'} = 'RARE';
$raritiesConversion{'M'} = 'MYTHIC';
$raritiesConversion{'Special'} = 'SPECIAL';
$raritiesConversion{'Bonus'} = 'BONUS';

# Get card name
my $cardName = $ARGV[0];
if (!$cardName) {
    print 'Enter a card name: ';
    $cardName = <STDIN>;
    chomp $cardName;
}


if (!exists $cards{$cardName}) {
    my $possible;
    foreach $possible (sort keys (%cards)) {
        if ($possible =~ m/$cardName/img && $cardName =~ m/..../) {
            print ("Did you mean $possible ?\n");
        }
    }
    die "Card name doesn't exist: $cardName\n";
}

my $cardTemplate = 'cardClass.tmpl';
my $splitDelimiter = '//';
my $empty = '';
my $splitSpell = 'false';
my $originalName = $cardName;

# Remove the // from name of split cards
if (index($cardName, $splitDelimiter) != -1) {
    $cardName  =~ s/$splitDelimiter/$empty/g;
    $cardTemplate = 'cardSplitClass.tmpl';
    $splitSpell = 'true';
}


# Check if card is already implemented
my $fileName = "../Mage.Sets/src/mage/cards/".lc(substr($cardName, 0, 1))."/".toCamelCase($cardName).".java";
if(-e $fileName) {
  die "$cardName is already implemented.\n$fileName\n";
}
                                                                          
# Generate lines to corresponding sets
my %vars;
$vars{'className'} = toCamelCase($cardName);
$vars{'cardNameFirstLetter'} = lc substr($cardName, 0, 1);
my @card;

foreach my $setName (keys %{$cards{$originalName}}) {
  my $setFileName = "../Mage.Sets/src/mage/sets/".$knownSets{$setName}.".java";
  @card = @{${cards{$originalName}{$setName}}};
  my $line = "        cards.add(new SetCardInfo(\"".$card[0]."\", ".$card[2].", Rarity.".$raritiesConversion{$card[3]}.", mage.cards.".$vars{'cardNameFirstLetter'}.".".$vars{'className'}.".class));\n";
  @ARGV = ($setFileName);
  $^I = '.bak';
  my $last;
  my $lastName;
  my $currName;
  while (<>) {
    if ($_ !~ m/cards.add/) {      
      if (defined($last)) {
        print $last; 
      }
      # print card line as last
      if (defined($currName) && ($cardName cmp $currName) > 0) {
        print $line;
        undef $currName;
      }
      $last = $_;
      print $last if eof;   
      next;
    }
    
    ($lastName) = $last =~ m/"(.*?)"/;
    ($currName) = $_ =~ m/"(.*?)"/;
    print $last;
    $last = $_;
    # print card line as first
    if (!defined($lastName) && defined($currName) && ($currName cmp $cardName) > 0) {
      print $line;
    }
    # print card line in the middle
    if (defined($lastName) && defined($currName) && ($cardName cmp $lastName) > 0 && ($currName cmp $cardName) > 0) {
      print $line;
    }
  }

  unlink $setFileName.".bak";
  print "$setFileName\n";
}

# Generate the card
my $result;
my $template = Text::Template->new(TYPE => 'FILE', SOURCE => $cardTemplate, DELIMITERS => [ '[=', '=]' ]);
$vars{'author'} = $author;
$vars{'manaCost'} = fixCost($card[4]);
$vars{'power'} = $card[6];
$vars{'toughness'} = $card[7];

my @types;
$vars{'planeswalker'} = 'false';
$vars{'subType'} = '';
$vars{'hasSubTypes'} = 'false';
$vars{'hasSuperTypes'} = 'false';
my $cardAbilities = $card[8];
my $type = $card[5];
while ($type =~ m/([a-zA-Z]+)( )*/g) {
    if (exists($cardTypes{$1})) {
        push(@types, $cardTypes{$1}); 
        if ($cardTypes{$1} eq $cardTypes{'Planeswalker'}) {
            $vars{'planeswalker'} = 'true';
            $cardAbilities = $card[7];
        }
    } else {
        if (@types) {
            my $st = uc($1);
            $vars{'subType'} .= "\n        this.subtype.add(SubType.$st);";
			$vars{'hasSubTypes'} = 'true';
        } else {
            my $st = uc($1);
            $vars{'subType'} .= "\n        this.addSuperType(SuperType.$st);";
			$vars{'hasSuperTypes'} = 'true';
        }
    }
}

$vars{'type'} = join(', ', @types);
$vars{'abilitiesImports'} = '';
$vars{'abilities'} = '';

my $strong = "<strong>";
$cardAbilities  =~ s/$strong/$empty/g;
$strong = "</strong>";
$cardAbilities  =~ s/$strong/$empty/g;
$cardAbilities =~ s/\&/\|/g;

my @abilities = split('\$', $cardAbilities);
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
                    $vars{'abilities'} .= "\n        // " . ucfirst($kwUnchanged);
                    if ($keywords{$kw} eq 'instance') {
                        $vars{'abilities'} .= "\n        this.addAbility(" . $kw . "Ability.getInstance());";
                    } elsif ($keywords{$kw} eq 'new') {
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . "Ability());";
                    } elsif ($keywords{$kw} eq 'card') {
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . "Ability(this));";
                    } elsif ($keywords{$kw} eq 'color') {
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . "Ability(this.color));";
                    } elsif ($keywords{$kw} eq 'number') {
                        $ability =~ m/(\b\d+?\b)/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(' . $1 . '));';
                    } elsif ($keywords{$kw} eq 'card, number') {
                        $ability =~ m/(\b\d+?\b)/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(this, ' . $1 . '));';
                    } elsif ($keywords{$kw} eq 'cost') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(new ManaCostsImpl<>("' . fixCost($1) . '")));';
                        $vars{'abilitiesImports'} .= "\nimport mage.abilities.costs.mana.ManaCostsImpl;";
                    } elsif ($keywords{$kw} eq 'card, manaString') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(this, "' . fixCost($1) . '"));';
                    } elsif ($keywords{$kw} eq 'card, cost') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(this, new ManaCostsImpl<>("' . fixCost($1) . '")));';
                        $vars{'abilitiesImports'} .= "\nimport mage.abilities.costs.mana.ManaCostsImpl;";
                    } elsif ($keywords{$kw} eq 'number, cost, card') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(_, new ManaCostsImpl<>("' . fixCost($1) . '"), this));';
                        $vars{'abilitiesImports'} .= "\nimport mage.abilities.costs.mana.ManaCostsImpl;";
                    } elsif ($keywords{$kw} eq 'cost, card') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(new ManaCostsImpl<>("' . fixCost($1) . '"), this));';
                        $vars{'abilitiesImports'} .= "\nimport mage.abilities.costs.mana.ManaCostsImpl;";
                    } elsif ($keywords{$kw} eq 'type') {
                        $ability =~ m/\s([a-zA-Z\s]*)/g;
                        if ($1 =~ m/(^.*\s.*)/g) {
                            $vars{'abilities'} .= "\n        TargetPermanent auraTarget = new TargetPermanent(filter);";
                        } else {
                            $vars{'abilities'} .= "\n        TargetPermanent auraTarget = new Target". toCamelCase($1) . "Permanent();";
                            $vars{'abilitiesImports'} .= "\nimport mage.target.common.Target". toCamelCase($1) . "Permanent;";
                        }
                        $vars{'abilities'} .= "\n        this.getSpellAbility().addTarget(auraTarget);";
                        $vars{'abilities'} .= "\n        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));";
                        $vars{'abilities'} .= "\n        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));";
                        $vars{'abilitiesImports'} .= "\nimport mage.abilities.effects.common.AttachEffect;";
                        $vars{'abilitiesImports'} .= "\nimport mage.constants.Outcome;";
                        $vars{'abilitiesImports'} .= "\nimport mage.target.TargetPermanent;";
                    } elsif ($keywords{$kw} eq 'manaString') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability("' . fixCost($1) . '"));';
                    }
                    $vars{'abilitiesImports'} .= "\nimport mage.abilities.keyword." . $kw . "Ability;";
                } else {
                    $vars{'abilities'} .= "\n        // $kwUnchanged";
                }
                $vars{'abilities'} .= "\n";
            }
        }
    }

    if (!$notKeyWord) {
        $vars{'abilities'} .= "\n        // $ability";
    }
}
if ($vars{'abilities'}) {
    $vars{'abilities'} = "\n" . $vars{'abilities'};
}

$result = $template->fill_in(HASH => \%vars);

open CARD, "> $fileName";
print CARD $result;
close CARD;

print "$fileName\n";
