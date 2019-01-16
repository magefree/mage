#!/usr/bin/perl -w

#author: spjspj

use strict;

my $addedCards;
my $GIT_CMD = "git.exe";

my $text = `\"$GIT_CMD\" tag`;
print "Assuming the tag command is on: \"$GIT_CMD\" tag\n";
my @lines = split /\n/, $text;
my %order_of_tags;

my $tag;
foreach $tag (@lines)
{
    my $orig_num = $tag;
    my $num = $tag;
    if ($num =~ m/(\d+)\.(\d+).(\d+)v(\d+)/img)
    {
        $num = $1 * 2000 + $2 * 100 + $3 * 20 + $4;
        $order_of_tags {$num} = $tag;
    }
}

my $num;
my $number = 0;
my %new_order;
foreach $num (sort {$a<=>$b} keys (%order_of_tags))
{
    $number++;
    $new_order{$number} = "$order_of_tags{$num}, press $number:\n";
}

print ("To generate from head to :\n");
foreach $num (sort {$a<=>$b} keys (%new_order))
{
    if ($num > $number - 10)
    {
        print ("   $new_order{$num}");
    }
}

print ("Choose your preferred tag: ");

my $cmd = <STDIN>;
chomp $cmd;

my %cn_classes;
sub read_all_card_names
{
    print ("find \"add\" ..\\Mage.Sets\\src\\mage\\sets\\*.java\n");
    my $all_cards = `find \"add\" ..\\Mage.Sets\\src\\mage\\sets\\*.java`;
    my @cards = split /\n/, $all_cards;
    my $card;
    foreach $card (sort @cards)
    {
        if ($card =~ m/.*SetCardInfo."([^"]+)".*\.([^\.]+).class/)
        {
            $cn_classes {$2} = $1;
        }
    }
}
read_all_card_names();

sub get_name_of_card_from_class
{
    my $line = $_ [0];
    if ($line =~ m/Mage.Sets.*[\/\\]([^\/\\]+)\.java/img)
    {
        my $class_name = $1;
        my $card_name = $class_name;
        if (exists ($cn_classes {$card_name}))
        {
            return ($cn_classes {$card_name});
        }
        $card_name =~ s/(.)([A-Z])/$1 $2/g;
        $card_name =~ s/\d//g;
        $card_name =~ s/ The / the /g;
        $card_name =~ s/ Of / of /g;
        $card_name =~ s/ To / to /g;
        $card_name =~ s/ And / and /g;
        $card_name =~ s/ For / for /g;
        return $card_name;
    }
    return "";
}

if (exists ($new_order{$cmd}))
{
    my $tag = $new_order{$cmd};
    $tag =~ s/You chose //;
    $tag =~ s/,.*//;
    chomp $tag;
    print ("You chose $tag\n");

    print "Command is \"$GIT_CMD\" diff $tag..HEAD\n";
    $text = `\"$GIT_CMD\" diff $tag..HEAD`;
    @lines = split /\n/, $text;
    my $line;
    my $use_next_line = 0;
    my $past_line = 0;
    my %new_cards;

    foreach $line (@lines)
    {
        chomp $line;
        if ($line =~ m/\-\-\-.*dev.*null/)
        {
            $use_next_line = 1;
        }
        elsif ($use_next_line)
        {
            if ($line =~ m/sets.*mage.cards\/[a-z]\//img)
            {
                $new_cards {get_name_of_card_from_class($line)} ++;
            }
            $use_next_line = 0;
        }
        if ($line =~ m/deleted file/)
        {
            if ($past_line =~ m/sets.*mage.cards\/[a-z]\//img)
            {
                $new_cards {get_name_of_card_from_class($past_line)} --;
            }
        }
        $past_line = $line;
    }

    open MTG_CARDS_DATA, "mtg-cards-data.txt";
    my %all_cards;
    while (<MTG_CARDS_DATA>)
    {
        my $val = $_;
        $val =~ s/\|/xxxx/;
        $val =~ s/\|.*//;
        $val =~ m/^(.*)xxxx(.*)/;
        $all_cards {$1} = $2;
    }

    print ("Found these new card names!\n");
    foreach $line (sort keys (%new_cards))
    {
        if ($new_cards {$line} > 0)
        {
            my $setname = $all_cards {$line};

            # Check if name is correct if not try to fix with ' before the endings of first word
            no warnings 'uninitialized';
            if (!(length $setname)) { # setname is not set correct
               my $firstblank = index($line, " ");
               my $char  = substr $line, $firstblank - 1, 1;
               if ($char eq "s") {
                 my $fixedname = (substr $line, 0, $firstblank - 1) . "'" . (substr $line , $firstblank -1);
                 $setname = $all_cards {$fixedname};
                 if (length $setname) {
                    $line = $fixedname;
                 }
               }
            }


            if (!(length $setname)){
                print ("*** Set not found - probably card name is not exactly correct\n");
                print ($line, "\n");
            } else {
                print ($line, " in ", $setname, "\n");
            }


        }
    }
}
