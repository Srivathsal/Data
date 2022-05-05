##################################################
# Name: scm_get_pom_version_number.pl
# Usage
# Author:JanJaap Lahpor
#
##################################################
#!/usr/bin/perl

#Declare variables
$readFile="@ARGV[0]";
$writeFile="@ARGV[1]";

#Read in pom.xml content
open(READFILE, $readFile) || die("Could not open file!");
@pomData=<READFILE>;
close(READFILE);

#Create base_version_number.txt
open (WRITEFILE, '>' . $writeFile);

foreach (@pomData){
	if($_ =~ m/<version>(.*)-SNAPSHOT<\/version>/){
		print WRITEFILE $1;
		last;
	}
}

close(WRITEFILE);