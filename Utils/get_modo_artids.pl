#!/usr/bin/perl
##
#   File : get_modo_artids.pl
#   Date : 12/Nov/2017
#   Author : spjspj
#   Purpose : Get the artid for each face art of each card from modo (mtgo)
#             this then goes into some form of image such as:
#             http://mtgoclientdepot.onlinegaming.wizards.com/Graphics/Cards/Pics/161922_typ_reg_sty_050.jpg
#              "" /00100_typ_reg_sty_001.jpg
#              "" /01440_typ_reg_sty_010.jpg
#              "" /102392_typ_reg_sty_010.jpg
#              "" /400603_typ_reg_sty_050.jpg
#             This program expects that you've copied the relevant .xml files from an installation of mtgo 
#             into the current working directory for this perl script:
#                 For example: CARDNAME_STRING.xml, client_BNG.xml, 
#             Also, it expects to be running on windows
##  

use strict;
use LWP::Simple;
use POSIX qw(strftime);


# From xmage code - fix from MODO to XMAGE type set codes
my %fix_set_codes;
$fix_set_codes {"2U"} = "2ED";
$fix_set_codes {"3E"} = "3ED";
$fix_set_codes {"4E"} = "4ED";
$fix_set_codes {"5E"} = "5ED";
$fix_set_codes {"6E"} = "6ED";
$fix_set_codes {"7E"} = "7ED";
$fix_set_codes {"AL"} = "ALL";
$fix_set_codes {"AP"} = "APC";
$fix_set_codes {"AN"} = "ARN";
$fix_set_codes {"AQ"} = "ATQ";
$fix_set_codes {"CM1"} = "CMA";
$fix_set_codes {"DD3_DVD"} = "DVD";
$fix_set_codes {"DD3_EVG"} = "EVG";
$fix_set_codes {"DD3_GLV"} = "GLV";
$fix_set_codes {"DD3_JVC"} = "JVC";
$fix_set_codes {"DK"} = "DRK";
$fix_set_codes {"EX"} = "EXO";
$fix_set_codes {"FE"} = "FEM";
$fix_set_codes {"HM"} = "HML";
$fix_set_codes {"IA"} = "ICE";
$fix_set_codes {"IN"} = "INV";
$fix_set_codes {"1E"} = "LEA";
$fix_set_codes {"2E"} = "LEB";
$fix_set_codes {"LE"} = "LEG";
$fix_set_codes {"MI"} = "MIR";
$fix_set_codes {"MM"} = "MMQ";
$fix_set_codes {"MPS_KLD"} = "MPS";
$fix_set_codes {"NE"} = "NEM";
$fix_set_codes {"NE"} = "NMS";
$fix_set_codes {"OD"} = "ODY";
$fix_set_codes {"PR"} = "PCY";
$fix_set_codes {"PS"} = "PLS";
$fix_set_codes {"P2"} = "PO2";
$fix_set_codes {"PO"} = "POR";
$fix_set_codes {"PK"} = "PTK";
$fix_set_codes {"ST"} = "STH";
$fix_set_codes {"TE"} = "TMP";
$fix_set_codes {"CG"} = "UDS";
$fix_set_codes {"UD"} = "UDS";
$fix_set_codes {"GU"} = "ULG";
$fix_set_codes {"UZ"} = "USG";
$fix_set_codes {"VI"} = "VIS";
$fix_set_codes {"WL"} = "WTH";

# Main
{
    my $card = $ARGV [0];

    # Example: <CARDNAME_STRING_ITEM id='ID258_461'>Abandoned Sarcophagus</CARDNAME_STRING_ITEM>
    my $vals = `find /I "CARDNAME" *CARDNAME*STR*`;
    my %ids;
    my %set_names;
    my $count = 0;
    while ($vals =~ s/^.*CARDNAME_STRING_ITEM id='(.*?)'>(.*?)<\/CARDNAME_STRING.*\n//im)
    {
        $ids {$1} = $2;
        $count++;
    }
    print ("Finished reading $count names\n"); 


    #$vals = `find /I "<" *client*`;
    $vals = `findstr /I "CARDNAME_STRING DIGITALOBJECT ARTID CLONE FRAMESTYLE " *lient* | find /I /V "_DO.xml"`;

    my $current_artid = "";
    my $current_clone_id = "";
    my $current_doc_id = "";
    my $current_line = "";
    my $current_name = "";
    my $current_set = "";
    my $num_set = 1;
    my %docid_to_clone;

    while ($vals =~ s/^(.*)\n//im)
    {
        my $line = $1;
        $line =~ m/^client_(.*)?\.xml/;
        $current_set = $1;
        
#<DigitalObject DigitalObjectCatalogID='DOC_26588'> *** IN
#   <ARTID value='100691'/> *** IN
#   <ARTIST_NAME_STRING id='ID257_266'/>
#   <CARDNAME_STRING id='ID258_12464'/> *** IN
#   <CARDNAME_TOKEN id='ID259_12464'/>
#   <CARDSETNAME_STRING id='ID516_103'/>
#   <CARDTEXTURE_NUMBER value='53176'/>
#   <COLLECTOR_INFO_STRING value='150/180'/>
#   <COLOR id='ID517_6'/>
#   <COLOR_IDENTITY id='ID518_4'/>
#   <CONVERTED_MANA_COST id='ID520_3'/>
#   <CREATURE_TYPE_STRING0 id='ID777_71'/>
#   <CREATURE_TYPEID0 id='ID521_12'/>
#   <FRAMESTYLE value='7'/>
#   <IS_CREATURE value='1'/>
#   <MANA_COST_STRING id='ID1811_114'/>
#   <POWER value='4'/>
#   <POWERTOUGHNESS_STRING value='4/5'/>
#   <RARITY_STATUS id='ID1818_3'/>
#   <REAL_ORACLETEXT_STRING id='ID1819_11686'/>
#   <SHOULDWORK value='1'/>
#   <SHROUD value='1'/>
#   <TOUGHNESS value='5'/>
#   <WATERMARK value='0'/>
#</DigitalObject>

        if ($line =~ m/<DigitalObject DigitalObjectCatalogID='([^']+)'/)
        {
            $current_line = "$current_set;$1;";
            $current_doc_id = $1; 
        }
        if ($line =~ m/\s*<ARTID value='(\d+)'/)
        {
            $current_line .= "artid=$1;";
        }
        if ($line =~ m/CARDNAME_STRING id='([^']+)'/)
        {
            $current_line = "$ids{$1};$current_line;($1)";
            $current_name = "$ids{$1}";
        }
        if ($line =~ m/CLONE_ID value='([^']+)'/)
        {
            my $clone_id = $1;
            $current_line .= ";Clone=($clone_id)";
            $current_clone_id = $clone_id;
        }

        if ($line =~ m/<\/DigitalObject/)
        {
            if ($current_name =~ m/.+/ && $current_doc_id =~ m/.+/)
            {
                $docid_to_clone {$current_doc_id} = $current_name;
            }
                
            $current_line = "";
            $current_name = "";
            $current_clone_id = "";
            $current_doc_id = "";
        }
    }
   
    # Run it again..
    $vals = `findstr /I "CARDNAME_STRING DIGITALOBJECT ARTID CLONE FRAMESTYLE" *lient* | find /I /V "_DO.xml"`;

    $current_set = "";
    $num_set = 1;
    $current_artid = "";
    $current_clone_id = "";
    $current_doc_id = "";
    $current_line = "";
    $current_name = "";
    my %seen_artids;
    my $current_framestyle = "";

    my %framestyles;
    $framestyles {1} = "001";  # Pre-modern cards
    $framestyles {2} = "010";
    $framestyles {3} = "010";  # M15 cards
    $framestyles {4} = "013";
    $framestyles {5} = "020";
    $framestyles {6} = "010";
    $framestyles {7} = "020";
    $framestyles {8} = "010";
    $framestyles {9} = "010";
    $framestyles {10} = "030"; # "040";
    $framestyles {11} = "010"; # Avatars
    $framestyles {12} = "010";
    $framestyles {13} = "050";
    $framestyles {14} = "010"; # Tokens
    $framestyles {15} = "010"; # Tokens as well
    $framestyles {18} = "010";
    $framestyles {19} = "030"; # "040";
    $framestyles {19} = "010";
    $framestyles {20} = "030";
    $framestyles {22} = "010";
    $framestyles {23} = "010";
    $framestyles {24} = "010";
    $framestyles {25} = "050";
    $framestyles {26} = "010";
    $framestyles {27} = "010";
    $framestyles {28} = "010";
    $framestyles {30} = "050";
    $framestyles {31} = "010"; # New cards
    $framestyles {34} = "030"; # "040";
    $framestyles {35} = "030"; # "040";
    $framestyles {36} = "050";
    $framestyles {37} = "010";
    $framestyles {38} = "010";
    $framestyles {39} = "050";
    $framestyles {42} = "010";
    $framestyles {43} = "010";
    $framestyles {45} = "030";
    $framestyles {45} = "030"; # "040";
    $framestyles {46} = "050";
    $framestyles {47} = "020"; # Expedition lands
    $framestyles {48} = "020";
    $framestyles {49} = "030"; # "040";
    $framestyles {50} = "030";
    $framestyles {51} = "050";
    $framestyles {52} = "010";
    $framestyles {53} = "050";
    $framestyles {54} = "010";


    my %types;
    my $current_type = "reg";
    $types {4} = "flip";
    $types {10} = "planeswk";
    $types {19} = "planeswk";
    $types {20} = "planeswk";
    $types {34} = "planeswk";
    $types {35} = "planeswk";
    $types {45} = "planeswk";
    $types {49} = "planeswk";
    $types {50} = "planeswk";


    while ($vals =~ s/^(.*)\n//im)
    {
        my $line = $1;
        $line =~ m/^client_(.*)?\.xml/;
        $current_set = $1;
        if (defined ($fix_set_codes {$current_set}))
        {
            $current_set = $fix_set_codes {$current_set};
        }
        
        if ($line =~ m/<DigitalObject DigitalObjectCatalogID='([^']+)'/)
        {
            $current_line = "$current_set;$1;";
            $current_doc_id = $1; 
        }
        if ($line =~ m/\s*<ARTID value='(\d+)'/)
        {
            $current_line .= "artid=$1;";
            $current_artid = $1;
        }
        if ($line =~ m/CARDNAME_STRING id='([^']+)'/)
        {
            $current_line = "$ids{$1};$current_line;($1)";
            $current_name = "$ids{$1}";
        }
        if ($line =~ m/CLONE_ID value='([^']+)'/)
        {
            my $clone_id = $1;
            $current_line .= ";Clone=($clone_id)";
            $current_clone_id = $clone_id;
        }
        if ($line =~ m/FRAMESTYLE value='([^']+)'/)
        {
            my $val = $1;
            $current_framestyle = $framestyles {$val};
            $current_type = $types {$val};

            if ($current_framestyle =~ m/^$/)
            {
                print (" ERROR: $current_framestyle not known: ($line) --- ");
                print ("  http://mtgoclientdepot.onlinegaming.wizards.com/Graphics/Cards/Pics/0000$current_artid" . "_typ_$current_type" . "_sty_$current_framestyle.jpg\n");
            }
            if ($current_type =~ m/^$/)
            {
                $current_type = "reg";
            }
        }

        if ($line =~ m/<\/DigitalObject/)
        {
            if ($docid_to_clone {$current_doc_id} =~ m/.+/) 
            {
                $current_name = "$docid_to_clone{$current_doc_id}";
            }
            if ($current_name =~ m/.+/ && $current_doc_id =~ m/.+/)
            {
                $docid_to_clone {$current_doc_id} = $current_name;
            }
            if ($current_name =~ m/^$/)
            {
                $current_name = $docid_to_clone {$current_clone_id};
            }
                
            if ($current_name =~ m/..*/ && $current_set =~ m/..*/ && $current_artid =~ m/..*/)
            {
                print ("$current_name;$current_set;$current_artid\n");
                # cut.pl statement..
                #   echo "1" | cut.pl stdin "http://mtgoclientdepot.onlinegaming.wizards.com/Graphics/Cards/Pics/00010_typ_reg_sty_001.jpg" "ME4\Badlands.jpg" wget_image
                if (!defined ($seen_artids {$current_artid}))
                {
                    $seen_artids {$current_artid} = "$current_set\\$current_name.jpg";
                    if ($current_artid < 10)
                    {
                        print ("  echo \"1\" | cut.pl stdin \"http://mtgoclientdepot.onlinegaming.wizards.com/Graphics/Cards/Pics/0000$current_artid" . "_typ_$current_type" . "_sty_$current_framestyle.jpg\" \"$current_set\\$current_name.jpg\" wget_image\n");
                    }
                    if ($current_artid < 100)
                    {
                        print ("  echo \"1\" | cut.pl stdin \"http://mtgoclientdepot.onlinegaming.wizards.com/Graphics/Cards/Pics/000$current_artid" . "_typ_$current_type" . "_sty_$current_framestyle.jpg\" \"$current_set\\$current_name.jpg\" wget_image\n");
                    }
                    elsif ($current_artid < 1000)
                    {
                        print ("  echo \"1\" | cut.pl stdin \"http://mtgoclientdepot.onlinegaming.wizards.com/Graphics/Cards/Pics/00$current_artid" . "_typ_$current_type" . "_sty_$current_framestyle.jpg\" \"$current_set\\$current_name.jpg\" wget_image\n");
                    }
                    elsif ($current_artid < 10000)
                    {
                        print ("  echo \"1\" | cut.pl stdin \"http://mtgoclientdepot.onlinegaming.wizards.com/Graphics/Cards/Pics/0$current_artid" . "_typ_$current_type" . "_sty_$current_framestyle.jpg\" \"$current_set\\$current_name.jpg\" wget_image\n");
                    }
                    else
                    {
                        print ("  echo \"1\" | cut.pl stdin \"http://mtgoclientdepot.onlinegaming.wizards.com/Graphics/Cards/Pics/$current_artid" . "_typ_$current_type" . "_sty_$current_framestyle.jpg\" \"$current_set\\$current_name.jpg\" wget_image\n");
                    }
                }
                else
                {
                    print ("   copy \"$seen_artids{$current_artid}\" \"$current_set\\$current_name.jpg\"\n");
                }
            }

            $current_artid = "";
            $current_clone_id = "";
            $current_doc_id = "";
            $current_line = "";
            $current_name = "";
            $current_type = "";
            $current_framestyle = "";
        }
    }
}
