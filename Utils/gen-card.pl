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
    $string =~ s/[-,\s\':]//g;
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
    chomp $author;
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
    #print "$data[0]--$data[1]\n"
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
if(!$cardName) {
    print 'Enter a card name: ';
    $cardName = <STDIN>;
    chomp $cardName;
}

if (!exists $cards{$cardName}) {
    die "Card name doesn't exist: $cardName\n";
}

# Check if card is already implemented
my $fileName = "../Mage.Sets/src/mage/cards/".substr($cardName, 0, 1)."/".toCamelCase($cardName).".java";
if(-e $fileName) {
  die "$cardName is already implemented.\n";
}
                                                                          
# Generate lines to corresponding sets
my %vars;
$vars{'className'} = toCamelCase($cardName);
$vars{'cardNameFirstLetter'} = lc substr($cardName, 0, 1);
my @card;

foreach my $setName (keys %{$cards{$cardName}}) {
  my $setFileName = "../Mage.Sets/src/mage/sets/".$knownSets{$setName}.".java";
  @card = @{${cards{$cardName}{$setName}}}; 
  my $line = "\tcards.add(new SetCardInfo(\"".$card[0]."\", ".$card[2].", Rarity.".$raritiesConversion{$card[3]}.", mage.cards.".$vars{'cardNameFirstLetter'}.".".$vars{'className'}.".class));\n";  
  
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

# Generate the the card
my $result;
my $template = Text::Template->new(TYPE => 'FILE', SOURCE => 'cardClass.tmpl', DELIMITERS => [ '[=', '=]' ]);
$vars{'author'} = $author;
$vars{'manaCost'} = fixCost($card[4]);
$vars{'power'} = $card[6];
$vars{'toughness'} = $card[7];

my @types;
$vars{'subType'} = '';
my $type = $card[5];
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

$vars{'abilitiesImports'} = '';
$vars{'abilities'} = '';

my @abilities = split('\$', $card[8]);
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
                    } elsif ($keywords{$kw} eq 'color') {
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . "Ability(this.color));";
                    } elsif ($keywords{$kw} eq 'number') {
                        $ability =~ m/(\b\d+?\b)/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(' . $1 . '));';
                    } elsif ($keywords{$kw} eq 'cost') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(new ManaCostsImpl("' . fixCost($1) . '")));';
                        $vars{'abilitiesImports'} .= "\nimport mage.abilities.costs.mana.ManaCostsImpl;";
                    } elsif ($keywords{$kw} eq 'card, manaString') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(this, "' . fixCost($1) . '"));';
                    } elsif ($keywords{$kw} eq 'card, cost') {
                        $ability =~ m/({.*})/g;
                        $vars{'abilities'} .= "\n        this.addAbility(new " . $kw . 'Ability(this, new ManaCostsImpl("' . fixCost($1) . '")));';
                        $vars{'abilitiesImports'} .= "\nimport mage.abilities.costs.mana.ManaCostsImpl;";
                    }
                    $vars{'abilitiesImports'} .= "\nimport mage.abilities.keyword." . $kw . "Ability;";
                } else {
                    $vars{'abilities'} .= "\n        // $kwUnchanged";
                }
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
