#!/usr/bin/perl

use 5.010;
use Compress::Raw::Zlib;
use Digest::MD5 qw(md5 md5_hex md5_base64);
use File::Copy;
use LWP::Simple;
use MIME::Base64 qw(encode_base64url decode_base64url);
use POSIX qw(strftime);
use Socket;
use bytes;
use strict;
use warnings;

my ($infile, $outfile) = @ARGV;
die "Usage: $0 INFILE OUTFILE\n" if not $outfile;
 
open my $in, '<', $infile or die;
binmode $in;
 
my $cont = '';
 
while (1)
{
    my $success = read $in, $cont, 100, length ($cont);
    die $! if not defined $success;
    last if not $success;
}
close $in;
 
open my $out, '>', $outfile or die;
binmode $out;
print $out $cont;
close $out;

# Decode the Tj components of the streams
sub hex_val
{
    my $v = $_ [0];
    
    if ($v =~ m/\d/) { return $v; }
    if ($v eq "a") { return 10; }
    if ($v eq "b") { return 11; }
    if ($v eq "c") { return 12; }
    if ($v eq "d") { return 13; }
    if ($v eq "e") { return 14; }
    if ($v eq "f") { return 15; }
    return $v;
}

my $overall_text;
my $tj;
my $this_tj;
sub print_str
{
    my $str = $_ [0];
    my $orig_str = $str;
    my $this_s;

    while ($str =~ s/(.)(.)//)
    {
        my $a = hex_val ($1) * 16;
        my $b = hex_val ($2);
        #print ($a+$b, " ");
        $this_s .= chr ($a+$b);
    }

    if ($this_s =~ m/ +[A-Z]/)
    {
        $this_s .= "   $this_tj$orig_str<<\n";
        $this_tj = "   >> TJ=";
    }
    else
    {
        $this_tj .= "\n    str:($this_s) $orig_str :";
    }
    return $this_s; 
}

sub get_pdf_text
{
    my $text = $_ [0];
    # hh hh hh hh hh << hex based on two 
    while ($text =~ s/^(.*)\n//im)
    {
        my $line = $1;
        if ($line =~ m/<([0-9a-f]+)>.*?Tj/)
        {
            my $str = $1;
            my $this_s = print_str ($str);
            $overall_text .= $this_s;
        }
    }
}
# Done - Decode the Tj components of the streams

# Write out the chunks of stream??
my $keep = 1;
my $cont2 = $cont;
my $keep_cont2 = $cont2;

my $stream_r = qr/^.*?FlateDecode.*?[^d]stream/s;
my $endstream_after_r = qr/endstream.*/s;
my $endstream_before_r = qr/^.*?endstream/s;
my $newline = qr/\r\n/s;
my $o;

# MAIN
while ($keep)
{
    my $cont_two = 1;
    while ($cont2 =~ m/[^d]stream/im)
    {
        $cont2 =~ s/$stream_r//;
        $keep_cont2 = $cont2;
        print ("\n >>> " . length ($cont2));
        $cont2 =~ s/$endstream_after_r//;
        print ("\n 2>>> " . length ($cont2));
        $cont2 =~ s/$newline//img;
        $keep_cont2 =~ s/$endstream_before_r//;
        print ("\n 3>>> " . length ($keep_cont2));

        # Compressed
        my $outfile2 = "perl_stream.$keep.zip";
        open my $out, '>', $outfile2 or die;
        binmode $out;
        print $out $cont2;
        close $out;

        # Decompressed
        my $d = new Compress::Raw::Zlib::Inflate();
        my $output = $d->inflate ($cont2, $o);
        print " xxxx after inflate..>> $output \n";
        my $outfile2 = $outfile . ".$keep.txt";
        open my $out, '>', $outfile2 or die;
        binmode $out;
        print $out $o;
        close $out;

        get_pdf_text ($o);

        $keep++;
        $cont2 = $keep_cont2;
    }
    
    print ("$overall_text\n");
    $keep = 0;
}
 
$cont =~ s/\W/_/img;
$cont =~ s/___*/_/img;
#print $cont;

say length($cont);
print ("$infile >> ",  -s $infile, "\n");
print ("$outfile >> ",  -s $outfile, "\n");
