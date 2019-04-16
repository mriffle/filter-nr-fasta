[![Build Status](https://travis-ci.org/mriffle/filter-nr-fasta.svg?branch=master)](https://travis-ci.org/mriffle/filter-nr-fasta)

# filter-nr-fasta
Filter the NCBI nr FASTA database on any arbitrary taxonomy or source database.

Setup Instructions
===================

Download latest release
-------------------------------------
Grab the latest release of filterNRFASTA.jar from the latest releases page:
https://github.com/mriffle/filter-nr-fasta/releases

Ensure Java is Installed
--------------------------
Java 11 or higher. Download from https://www.java.com/en/download/

Get NCBI data files
--------------------

Only needs to be done periodically...

1. Download NCBI's nr FASTA: ftp://ftp.ncbi.nlm.nih.gov/blast/db/FASTA/nr.gz
You do not need to uncompress it.

2. Download NCBI's accession to taxonomy mapping:
ftp://ftp.ncbi.nih.gov/pub/taxonomy/accession2taxid/prot.accession2taxid.gz
You do not need to uncompress it.

3. Download NCBI's taxonomy hierarchy file: Either
ftp://ftp.ncbi.nih.gov/pub/taxonomy/new_taxdump/new_taxdump.tar.gz or
ftp://ftp.ncbi.nih.gov/pub/taxonomy/new_taxdump/new_taxdump.zip

   - Uncompress with tar -xvzf new_taxdump.tar.gz or unzip new_taxdump.zip
   - We are interested in the nodes.dmp file, rest can be erased


Run Program
==============

Note: This program can consume a lot of RAM, especially when filtering on very general
taxa (such as bacteria (NCBI taxonomy: 2)). If you run out of RAM (heap space) while
running this program, give Java more RAM by using the -Xmx parameter as shown in the
bacteria example below.

You will need to know the NCBI taxonomy ID(s) you want to filter on. You can look them up
at https://www.ncbi.nlm.nih.gov/taxonomy

Get Help
---------
```
java -jar filterNRFASTA.jar
```
or
```
java -jar filterNRFASTA.jar -h
```

Output only human proteins (any source database)
------------------------------------------------
Outputs only sequences and accessions corresponding to human. Outputs as "human.nr.fasta" file.
```
java -jar downloadFilteredNR.jar
     -t 9606
     -a ../tax-dump-old/prot.accession2taxid.gz
     -n ../taxonomy_dump/nodes.dmp
     -f ../sequences/nr.gz >human.nr.fasta
```

Output all bacterial sequences/accessions present in refseq or uniprot
---------------------------------------------------------------------
Includes sequences and accessions corresponding to any bacteria. Outputs as
"bacteria-refseq-uniprot.nr.fasta" file.

This command also allocates 64 gigs of RAM for this Java process.
```
java -Xmx64g -jar downloadFilteredNR.jar
     -t 2
     -d refseq
     -d uniprot
     -a ../tax-dump-old/prot.accession2taxid.gz
     -n ../taxonomy_dump/nodes.dmp
     -f ../sequences/nr.gz >bacteria-refseq-uniprot.nr.fasta
```
