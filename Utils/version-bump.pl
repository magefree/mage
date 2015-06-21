#!/usr/bin/perl -w

use strict;
use File::Copy;
use File::Find;

open ROOTPOM, "< ../pom.xml" or die;

my $oldversion;
while (<ROOTPOM>) {
    my $line = $_;
    if ($line =~ m/mage-version>(.*)</) {
        print "Current version: $1\n";
        $oldversion = $1;
    }
}

print "Enter new version: ";
my $version = <STDIN>;
chomp $version;

find(\&update_in_finded, "..");
#update_version_in_java("../Mage.CLient/src/main/java/mage/client/MageFrame.java");
#update_version_in_java("../Mage.Server/src/main/java/mage/server/Main.java");
#update_version_in_java("../Mage.Server.Console/src/main/java/mage/server/console/ConsoleFrame.java");

sub update_in_finded {
    if (/pom\.xml$/ && !/^\.git/) {
        update_version($_);
    }
}

sub update_version {
    my ($filename) = @_;
    open POM, "< $filename" or die;
    open NEWPOM, "> $filename.new" or die;
    while (<POM>) {
        s/version>$oldversion/version>$version/g;
        print NEWPOM $_;
    }
    close POM;
    close NEWPOM;
    move("$filename.new", "$filename");
}

sub update_version_in_java {
    my ($filename) = @_;
    open FILE, "< $filename" or die;
    open NEWFILE, "> $filename.new" or die;
    $version =~m/(.)\.(.)\.(.)/;
    my ($f, $s, $t) = ($1, $2, $3);
    $oldversion =~m/(.)\.(.)\.(.)/;
    my ($of, $os, $ot) = ($1, $2, $3);
    print "f - $f, s - $s, t - $t\n";
    while (<FILE>) {
        s/new MageVersion\($of, $os, $ot,/new MageVersion\($f, $s, $t,/;
        print NEWFILE $_;
    }
    close FILE;
    close NEWFILE;
    move("$filename.new", "$filename");
}


