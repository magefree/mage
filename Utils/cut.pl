#!/usr/bin/perl
##
#   File : cut.pl
#   Author : spjspj
##  

use strict;
use LWP::Simple;
use POSIX qw(strftime);

# Main
{
    if (scalar (@ARGV) < 4)
    {
        print ("Usage: cut.pl <file> <term> <helper> <operation>!\n");
        print (" .   File can be - list, STDIN, or an actual file\n");
        print (" .   Term can be - a regex you're looking for\n");
        print (" .   Operation can be - grep, filegrep, count, size, strip_http, matrix_flip(for converting ringing touches!), oneupcount, wget\n");
        print (" .   Helper is dependent on the operation you're doing.  A number for grep will go +/- that amount \n");
        print ("   cut.pl bob.txt dave 5 grep\n");
        print ("   cut.pl all_java2.java TOKEN_STARTS_HERE TOKEN_ENDS_HERE grep_between\n");
        print ("   cut.pl full_text.txt keys 0 filegrep\n");
        print ("   cut.pl full_text.txt 0  0 make_code_bat\n");
        print ("   dir /a /b /s *.java | cut.pl stdin 0  0 make_code_bat > bob.bat\n");
        print ("   dir /a /b /s *.xml | cut.pl stdin 0  0 make_code_bat > bob_xml.bat\n");
        print ("   cut.pl d:\\perl_programs output.*txt  7 age_dir | cut.pl list . 0 grep\n");
        print ("   cut.pl bob.txt 0 0 uniquelines \n");
        print ("   cut.pl file 0 0 strip_http\n");
        print ("   cut.pl stdin \";;;\" \"1,2,3,4\" fields\n");
        print ("   cut.pl bob.txt 0 0 matrix_flip\n");
        print ("   cut.pl bob.txt 0 0 condense   (Used for making similar lines in files smaller..)\n");
        print ("   cut.pl bob.txt 0 0 str_condense   (Used for making similar lines in files smaller..)\n");
        print ("   cut.pl stdin \"http://bob.com/a=XXX.id\" 1000 oneupcount   \n");
        print ("   cut.pl stdin \"http://www.comlaw.gov.au/Details/XXX\" 1000 wget\n");
        print ("   cut.pl stdin \"https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=XXX\"  5274 oneupcount\n");
        print ("   cut.pl stdin \"https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=XXX'  5274 wget\n");
        print ("   cut.pl  modern_bluesa \";;;\" \"0,7\" fields | cut.pl stdin \";;;\" 3 wordcombos\n");
        print ("   cut.pl  modern_bluesa \";;;\" \"0,7\" fields | cut.pl stdin 0 0 uniquewords\n");
        print ("   cut.pl  modern_bluesa \";;;\" \"0,2\" images_html\n");
        print ("   cut.pl  stdin start_ _end letters\n");
        print ('   echo ""   | cut.pl stdin "http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=4554&start=30" 0 wget');
        print ("\n");
        print ("   cut.pl all_java.java \"\\\+\\\+\\\+\\\+\" \"extends token\" cut_on_first_display_with_second\n\n");
        print ("\n");
        print ('\necho "" | cut.pl stdin "http://www.slightlymagic.net/forum/viewtopic.php?f=70&t=14062&start=XXX" 400 oneupcount | cut.pl stdin "XXX" 400 wget\n');
        print ("\n");
        print ('\necho "" | cut.pl stdin "http://mythicspoiler.com/c17/cards/stalkingleonin.html" 0 wget_card_spoiler\n');
        print ('dir /a /b /s *.jar | cut.pl stdin "^" "7z l -r \""  replace | cut.pl stdin "$" "\"" replace > d:\temp\xyz.bat');
        print ("\n");
        print ('echo "1" | cut.pl stdin "https://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=16431&type=card" "6ED/Phantasmal Terrain.full.jpg" wget_image');
        print ("\n");
        exit 0;
    }

    my $file = $ARGV [0];
    my $term = $ARGV [1];
    my $helper = $ARGV [2];
    my $operation = $ARGV [3];
    my %combos;
    my %all_combos;
    my %dedup_line_hash;
    my $in_between_lines = 0;

    if ($file eq "list" && $operation ne "size")
    {
        while (<STDIN>)
        {
            chomp $_;
            my $file = $_;
            my $found_output = 0;
            #print "==========\n";
            #print "RUNNING: cut.pl $file $term $helper $operation \n";
            open PROC, "cut.pl $file $term $helper $operation |";
            while (<PROC>)
            {
                if ($found_output == 0)
                {
                    print ("\n\n==================\nProcessing file: $file\n");
                    $found_output = 1;
                }
                print ($_);
            }
            if ($found_output > 0)
            {
                print ("\n******************xx\n");
            }
            close PROC;
        }
       
        exit;
    }

    if ($file eq "stdin")
    {
        open FILE, "-";
    }
    elsif ($operation eq "strip_http")
    {
        open FILE, "$file";
        binmode (FILE);
    }
    elsif ($operation ne "age_dir")
    {
        open FILE, "$file";
    }
    else
    {
    }
    my $current_file = '';
    my $dot_current_file = '';
    my $in_file = 0;
    my $num_files = 0;

    # size functions!
    my $total_size = 0;

    # OldGrep functions!
    my %grep_past_lines;
    my $grep_past_lines_index  = 1;
    my $grep_forward_lines = -1;

    # Grep variables:
    # Before and or after!
    my $before = 0;
    my $before_index = 0;
    my $after = 0;
    my $orig_after = 0;
    my $after_index = 0;
    my @before_lines;
    my @after_lines;

    # Grep variables:
    # Check before all the time (from first line), but only checkafter after the first line is matched!
    my $use_before = 0;
    my $use_after = 0;
    my $num_lines_after = 0;

            if ($helper =~ m/^\d+$/)
            {
                $before = $helper;
                $use_before = 1;
                if ($helper eq "0")
                {
                    $use_before = 0;
                }
                $after = 0;
                $use_after = 1;
                $orig_after = $helper;
            }
            elsif ($helper =~ m/^-\d+/)
            {
                $before = -1 * $helper;
                $use_before = 1;
            }
            elsif ($helper =~ m/^\+\d+/)
            {
                $after = 0;
                $orig_after = $helper;
                $use_after = 1;
            }

    # Count functions!
    my $count = 1;
    my $seen_http = 0;
    my $lines_http = 0;
    my %matrix_flip;
    my $matrix_row = 0;
    my $matrix_col = 0;
    my $max_matrix_col = 0;
    my $condense_begin = 1;
    my $condense_line = "";
    my $condense_start = "";
    my $condense_regex = "";
    my $condense_count = 0;

    if ($operation eq "oneupcount")
    {
        my $i = 0;
        for ($i = 0; $i < $helper; $i ++)
        {
            my $l = $term;
            $l =~ s/XXX/$i/;
            print ("$l\n");
        }
        exit;
    }

    if ($operation eq "wget_seed")
    {
        my $i;
        for ($i = 10; $i < $helper + 10; $i++)
        {
            my $url = $term;
            $url = "http://gatherer.wizards.com/Pages/Card/Details.aspx?action=random";
            my $content = get $url;
            $content =~ s/\s\s/ /gim;
            $content =~ s/\s\s/ /gim;
            $content =~ s/\n//gim;
            $content =~ s/.*multiverseid=(\d+).*/$1/gim;

            print "$content\n";
        }
    }

    my %kkks;
    if ($operation eq "filegrep")
    {
        open KEYS, "$term";
        while (<KEYS>)
        {
            chomp;
            $kkks {"^$_"} = 1;
        }
    }
    if ($operation eq "make_code_bat")
    {
        print ("\@echo off\n");
    }

    my %ulines;
    my $ulines_count = 0;
    my @cut_on_term;
    my $saw_helper_cut_on_term = 0;
    while (<FILE>)
    {
        chomp $_;
        my $line = $_;

        if ($operation eq "grepold")
        {
            if ($line !~ m/$term/i && $grep_forward_lines < 0)
            {
                $grep_past_lines {$grep_past_lines_index} = $line;
                $grep_past_lines_index ++;
                if ($grep_past_lines_index > $helper)
                {
                    $grep_past_lines_index = 1;
                }
            }
            elsif ($line =~ m/$term/i)
            {
                my $i = $grep_past_lines_index;
                if (defined ($grep_past_lines {$i}))
                {
                    print $grep_past_lines {$i}, " --- 22222\n";
                }

                $i++;
                if ($i > $helper) { $i = 1; }

                while ($i != $grep_past_lines_index)
                {
                    if (defined ($grep_past_lines {$i}))
                    {
                        print $grep_past_lines {$i}, " --- 33333\n";
                    }
                    $i ++;
                    if ($i > $helper) { $i = 1; }
                }
                print "\n", $line, "\n";
                my %new_hash;
                %grep_past_lines = %new_hash;
                $grep_past_lines_index = 1;
                $grep_forward_lines = $helper + 1;
            }
           
            if ($grep_forward_lines <= $helper && $grep_forward_lines > 0)
            {
                print $line, "  --- 44444\n";
            }
            $grep_forward_lines--;
            if ($grep_forward_lines == 0)
            {
                print "\n";
            }
        }

        if ($operation eq "wget")
        {
            my $i;
            {
                my $url = $term;
                $url =~ s/XXX/$line/;
                print ("Looking at :$url:\n");
                my $content = get $url;
                die "Couldn't get $url" unless defined $content;
                $content =~ s/\s\s/ /gim;
                $content =~ s/\s\s/ /gim;
                $content =~ s/\n//gim;

                print $url, "\n\n\n\n\n", "=================\n", $content, "============\n";
            }
        }
        
        if ($operation eq "wget_card_spoiler")
        {
            my $i;
            {
                my $url = $term;
                $url =~ s/XXX/$line/;
                my $content = get $url;
                die "Couldn't get $url" unless defined $content;
                $content =~ s/\s\s/ /gim;
                $content =~ s/\s\s/ /gim;
                $content =~ s/\n//gim;
                $content =~ s/^.*CARD NAME-*->/CARD NAME-->/gim;
                $content =~ s/<!-*-END CARD.*$//gim;

                # Example: <!--CARD NAME-->Licia, Sanguine Tribune </font></td></tr><tr><td colspan="2" valign="top"> <!--MANA COST--> 5RWB </td></tr><tr><td colspan="2" valign="top"> <!--TYPE--> Legendary Creature - Vampire Soldier </td></tr><tr><td colspan="2" valign="top"> <!--CARD TEXT--> Licia, Sanguine Tribune costs 1 less to cast for each 1 life you gained this turn.  <br><br> First strike, lifelink <br><br> Pay 5 life: Put three +1/+1 counters on Licia. Activate this ability only on your turn and only once each turn.  </td></tr><tr><td colspan="2" valign="top"><i> <!--FLAVOR TEXT--> "I give my blood, my life, all I have in exchange for victory." </i></td></tr><tr><td colspan="2" valign="top"></td></tr><tr><td width="210" valign="bottom"><font size="1"> <!--ILLUS--> Illus. Magali Villeneuve <!--Set Number--><i> </i></font></td><td valign="bottom" align="left"><font size="2"> <!--P/T--> 4/4 <!--END CARD TEXT--><!--END CARD TEXT--><!--END CARD TEXT--><!--END CARD TEXT--><!--END CARD TEXT--><!--END CARD TEXT-->
                #    >>> Duelist's Heritage|Commander 2016|1|R|{2}{W}|Enchantment|||Whenever one or more creatures attack, you may have target attacking creature gain double strike until end of turn.|
                $content =~ m/.*CARD NAME-*-> *(.*?) *</im;
                my $card_name = $1;
                $content =~ m/.*MANA COST-*->(.*?)</im;
                my $mana_cost = $1;
                $mana_cost =~ s/ //gim;
                $mana_cost =~ s/(.)/{$1}/gim;
                $content =~ m/.*TYPE-*->(.*?)</im;
                my $type = $1;
                $content =~ m/.*TEXT-*->(.*?)<!-*-FLAVOR/im;
                my $text = $1;
                $text =~ s/<br[ \/]*?>/\$/img;
                $text =~ s/\$ *\$/\$/img;
                $text =~ s/<[^>]+>//img;
                $text =~ s/[\$ ]*$//img;
                $content =~ m/.*P\/T-*-> *(.*?) *\/ *(.*?) *(<|$)/im;
                my $p = $1;
                my $t = $2;
                print ("$card_name|Commander 2017|??|CURM|$mana_cost|$type|$p|$t|$text|\n");
            }
        }
        if ($operation eq "wget_image")
        {
            my $i;
            {
                if (!(-f "$helper"))
                {
                    my $url = $term;
                    print ("Download :$url:\n");
                    my $content = get $url;
                    print ("Saw " . length ($content) . " bytes!\n");
                    print ("Save in $helper\n");
                    open OUTPUT, "> " . $helper or die "No dice!";
                    binmode (OUTPUT);
                    print OUTPUT $content;
                    close OUTPUT;
                    print $url, " >>> ", $helper, "\n";
                }
                else
                {
                    print ("Found $helper existed already..\n");
                    if (-s "$helper" == 0)
                    {
                        `del "$helper"`;
                    }
                }
            }
        }
        if ($operation eq "grep")
        {
            if ($line !~ m/$term/i && $use_after && $after > 0)
            {
                print ($line, "\n");
                $after--;
                if ($after == 0)
                {
                    print ("aaa===================\n");
                }
            }

            if ($line !~ m/$term/i && $use_before)
            {
                $before_lines [$before_index] = $line;
                #print (" >>>>  adding in $before_index ($line)\n");
                #print (join (',,,', @before_lines));
                #print ("\n");
                $before_index ++;
                if ($before_index >= $before)
                {
                    $before_index = 0;
                }
            }

            if ($line =~ m/$term/i)
            {
                if ($use_before)
                {
                    #print ("bbb===================\n");
                    my $b = $before_index;
                    my $ok_once = 1;

                    while ($b != $before_index || $ok_once)
                    {
                        if (defined ($before_lines [$b]))
                        {
                            #print ("bbb" , $before_lines [$b], "\n");#.($b, .$before. $before_index, $ok_once).\n");
                            print ($before_lines [$b], "\n");#.($b, .$before. $before_index, $ok_once).\n");
                        }
                        $ok_once = 0;
                        if ($b >= $before - 1)
                        {
                            $b = -1;
                        }
                        $b++;
                    }
                    my @new_array;
                    @before_lines = @new_array;
                }
                print ("$line\n");
                if ($use_after)
                {
                     $after = $orig_after;
                }
            }
        }
        if ($operation eq "grep_between")
        {
            if ($line =~ m/$term/i)
            {
                print ("\n===================================================================\n");
                print ($line, "\n");
                $in_between_lines = 1;
            }
            if ($line !~ m/$helper/i && $in_between_lines)
            {
                print ($line, "\n");
            }
            if ($line =~ m/$helper/i && $in_between_lines)
            {
                print ($line, "\n");
                $in_between_lines = 0;
            }
        }

        if ($operation eq "filegrep")
        {
            my $k;
            my $print = 1;
            foreach $k (keys (%kkks))
            {
                if ($line =~ m/$k/ && $print)
                {
                    $print = 0;
                    print ($line, "\n");
                }
            }
        }

        if ($operation eq "size")
        {
            if (-f $line)
            {
                my $sizer = -s $line;
                my $zzz = "                             $sizer";
                $zzz =~ s/.*(........................)$/$1/;
                print ($zzz, " --- $line\n");
                $total_size += $sizer;
            }
        }

        if ($operation eq "count")
        {
            print ("$count - $line\n");
            $count++;
        }

        if ($operation eq "strip_http")
        {
            # Has to work on a file..
            if ($line =~ m/.*HTTP/)
            {
                $seen_http = 1;
                print ("SEEN HTTP\n");
            }

            #print (">>$line<<\n");
            $lines_http ++;

            if ($seen_http && $line eq "")
            {
                $seen_http = 2;
            }
        }

        if ($operation eq "replace")
        {
            my $orig_line = $line;
            $line =~ s/$term/$helper/gi;
            if ($helper =~ m/\\n/)
            {
                $line =~ s/\\n/\n/gi;
            }
            if ($helper =~ m/''/)
            {
                $line =~ s/''/"/gi;
            }
            print ("$line\n");

            eval("\$orig_line =~ s/$term/$helper/gi;");
            #print ("$orig_line\n");
        }
        if ($operation eq "dedup_line")
        {
            $line =~ m/::(.*)::/;
            my $user = $1;
            my $new_line;
            $line =~ s/.*://;
            while ($line =~ s/,([^,]*),/,/im)
            {
                $new_line .= "\n$user:$1\n";
                if (not defined ($dedup_line_hash {"$user:$1"}))
                {
                    $dedup_line_hash {"$user:$1"} = 1;
                    $dedup_line_hash {$user} ++;
                }
            }
            print ("$new_line\n");
        }

        if ($operation eq "matrix_flip")
        {
            my @chars = split //, $line;
            $matrix_col = 0;
            my $char;

            foreach $char (@chars)
            {
                $matrix_flip {"$matrix_row,$matrix_col"} = $char;
                $matrix_col ++;
                if ($max_matrix_col < $matrix_col)
                {
                    $max_matrix_col = $matrix_col;
                }
            }
            $matrix_row ++;
        }

        if ($operation eq "str_condense")
        {
            if ($line =~ m/(.)(\1{3,})/)
            {
                $line =~ s/(.)(\1{3,})/sprintf ("$1!%d#", length ($2));/eg;
            }
            print $line, "\n";
        }

        if ($operation eq "condense")
        {
            if ($condense_begin == 1)
            {
                #print (" begin........... $line \n");
                $condense_begin = 0;
                $condense_line = $line;
                $condense_start = $line;
                $condense_start =~ s/^(.{10,25}).*/$1/;
                $condense_start =~ s/\W/./g; 
                $condense_count = 0;
            }
            else
            {
                if ($line =~ $condense_start)
                {
                    #print (" similar........... $line \n");
                    $condense_count++;
                }
                else
                {
                    if ($condense_count > 1) 
                    {
                        $condense_line .= " {+similar=$condense_count}"; 
                    }
                    print $condense_line, "\n";

                    $condense_line = $line;
                    if ($condense_line !~ m/......./)
                    {
                        $condense_begin = 1;
                    }
                    else
                    {
                        $condense_start = $line;
                        $condense_start =~ s/^(.{10,25}).*/$1/;
                        $condense_start =~ s/\W/./g; 
                        $condense_count = 0;
                    }
                }
            }
        }

        if ($operation eq "fields")
        {
            #$line = "BBB$term$line$term";
            my @fs = split /$term/, $line;
            my @shows = split /,/, "$helper,";
            my $s;
            foreach $s (@shows)
            {
                if ($s eq "Rest") 
                {
                    print $line;
                }
                elsif ($s eq "NewLine") 
                {
                    print "\n";
                }
                else
                {
                    print $fs [$s], "$term";
                }
            }
            print "\n";
        }
        
        if ($operation eq "wordcombos")
        {
            my @fs = split /$term/, $line;

            # The first one is key, the rest need to be made into something
            my $current_key = $fs [0];
            my $current_val = $fs [1];
            
            $current_val =~ s/ /XXX/g;
            $current_val =~ s/\W//g;
            $current_val =~ s/XXX*/ /g;
            #print $current_key , " ---- ", $current_val, "\n";

            my @words = split / /, uc ($current_val);

            my $w;
            my $ws;
            for ($w = 0; $w < scalar (@words); $w++) 
            {
                my $x;
                $ws = $words [$w];
                for ($x = $w + 1; $x < $w + $helper; $x++)
                {
                    $ws .= "," . $words [$x];
                }
                #$ws .= ";;;" . $current_key;
                $combos {$ws} ++;
                $all_combos {$ws} .= ";;;" . $current_key;
            }

        }

        if ($operation eq "uniquewords")
        {
            $line .= " ";
            my @words = split / /, uc ($line);

            my $w;
            my $ws;
            for ($w = 0; $w < scalar (@words); $w++) 
            {
                $combos {$words [$w]} ++;
            }
        }

        if ($operation eq "cut_on_first_display_with_second")
        {
            if ($line =~ m/$helper/img) # cut_on_term
            {
                if ($saw_helper_cut_on_term) 
                {
                    print join ("\n", @cut_on_term);
                }
                else
                {
                    #print ("\nNothing in this segment!!\n");
                }
                $saw_helper_cut_on_term = 0; 
                my @new_array;
                @cut_on_term = @new_array;
            }
            push @cut_on_term, $line;
            if ($line =~ m/$term/img)
            {
                $saw_helper_cut_on_term = 1; 
            }
        }
         
        if ($operation eq "images_html")
        {
            my @fs = split /$term/, $line;
            my @shows = split /,/, "$helper,";
            my $s;
            {
                # <img src='http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=220251&type=card'/>
                if ($fs[$shows[0]] =~ m/\*/)
                {
                    my $id = $fs[$shows[0]];
                    $id =~ s/\*//g;
                    $id =~ s/ //g;
                    my $x = "<img src='http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=XXX&type=card'/>";
                    $x =~ s/XXX/$id/;
                    print "$fs[$shows[1]]<br>$x";
                    print "\n";
                }
            }
        }

        if ($operation eq "make_code_bat")
        {
            if ($line !~ m/all_/img) 
            {
                print ("echo \" $line +++++++\"\n");
                print ("type \"$line\"\n");
            }
        }

        if ($operation eq "uniquelines")
        {
            if (!defined ($ulines {$line}))
            {
                $ulines {$line} = 1;
                $ulines_count ++;
                print $line, "\n";
                #print "xxx $ulines_count\n";
            }
        }

        if ($operation eq "countlines")
        {
            $ulines {$line} ++;
        }
    }

    if ($operation eq "age_dir2")
    {
        opendir DIR, $file or die "cannot open dir $file: $!";
        print $file, "\n";
        my $nextFile;
        foreach $nextFile (grep {-f && ($helper > -M)} readdir DIR) 
        {
            #print $nextFile, " -- $helper - ", -M, "\n";
            if ($nextFile =~ m/$term/)
            {
                print "type $nextFile\n";
            }
        }
    }

    if ($operation eq "age_dir")
    {
        my $i;
        my $cmd = "type ";
        my $next_term = $term;

        for ($i = 0; $i < $helper; $i++) 
        {
            $next_term = $term;
            my $now = time();
            my $yyyymmdd = strftime "%Y%m%d", localtime($now - $i * 24*3600);
            $next_term =~ s/YYYYMMDD/$yyyymmdd/;
            $cmd .= " $next_term ";
        }
        print $cmd;
    }

    if ($operation eq "matrix_flip")
    {
        my $i;
        my $j;
        {
            for ($i = 0; $i < $max_matrix_col; $i++)
            {
                for ($j = 0; $j < $matrix_row; $j++)
                {
                    print ($matrix_flip {"$j,$i"});
                }
                print ("\n");
            }
        }
    }

    if ($operation eq "size")
    {
        print ($total_size, " --- Cumulative total\n");
    }

    close FILE;

    if ($operation eq "strip_http")
    {
        if ($seen_http == 2)
        {
            `tail +$lines_http > /tmp/_cut_file; chmod 777 /tmp/_cut_file`;
            `mv /tmp/_cut_file $file`;
        }
    }

    if ($operation eq "condense")
    {
        if ($condense_count > 1) 
        {
            $condense_line .= " {+similar=$condense_count}"; 
        }
        print $condense_line, "\n";
    }

    if ($operation eq "wordcombos")
    {
        my $v;
        my @keys = keys (%combos);
        my @new_keys;
        my $v = 0;
        my $k;

        foreach $k (@keys)
        {
            if ($k =~ m/,,/) { next; }
            #if ($k !~ m/WHENEVER/) { next; }
            #if ($combos {$k} > 10)
            {
                #push @new_keys, $combos {$k}; # . " ---- " . $k . ",,," . $all_combos {$k};
                push @new_keys, $k;
            }
            $v ++;
        }

        my @jjs = sort @new_keys;
        foreach $k (sort @jjs)
        {
            print $k, "\n";
        }
    }

    if ($operation eq "dedup_line")
    {
        my $k;
        for $k (sort keys (%dedup_line_hash))
        {
            if ($k !~ m/.*:.*/)
            {
                print ("$k ---> $dedup_line_hash{$k}\n");
            }
            if ($k =~ m/(.*):(.*)\s*$/)
            {
                if ($dedup_line_hash{$1} > 7)
                {
                    print ("/h $2\n");
                }
            }
        }
    }

    if ($operation eq "uniquewords")
    {
        my $v;
        my @keys = keys (%combos);
        my @new_keys;
        my $v = 0;
        my $k;

        my $i = 0;
        foreach $k (@keys)
        {
            $i ++;
            print $combos {$k}, ";  $k\n";
        }
    }

    if ($operation eq "countlines")
    {
        my $line;
        #foreach $line (sort {$a <=> $b} values %ulines)
        foreach $line (sort { $ulines{$a} <=> $ulines{$b} } keys %ulines)
        {
            print  ("$ulines{$line} ==== $line\n");
        }
    }

    if ($operation eq "letters")
    {
        #open PROC, "cut.pl $file $term $helper $operation |";
        my %as;
        $as {"A"} = 1;
        $as {"B"} = 1;
        $as {"C"} = 1;
        $as {"D"} = 1;
        $as {"E"} = 1;
        $as {"F"} = 1;
        $as {"G"} = 1;
        $as {"H"} = 1;
        $as {"I"} = 1;
        $as {"J"} = 1;
        $as {"K"} = 1;
        $as {"L"} = 1;
        $as {"M"} = 1;
        $as {"N"} = 1;
        $as {"O"} = 1;
        $as {"P"} = 1;
        $as {"Q"} = 1;
        $as {"R"} = 1;
        $as {"S"} = 1;
        $as {"T"} = 1;
        $as {"U"} = 1;
        $as {"V"} = 1;
        $as {"W"} = 1;
        $as {"X"} = 1;
        $as {"Y"} = 1;
        $as {"Z"} = 1;

        my $k;
        foreach $k (sort keys (%as))
        {
            my $k2;
            foreach $k2 (sort keys (%as))
            {
                print "$term$k$k2$helper\n";
            }
        }
    }
}
