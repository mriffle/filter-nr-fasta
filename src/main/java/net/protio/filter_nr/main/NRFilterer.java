package net.protio.filter_nr.main;

import net.protio.filter_nr.readers.AccessionTaxonomyIdReader;
import net.protio.filter_nr.readers.TaxonomyNodesReader;
import net.protio.filter_nr.utils.FASTAUtils;
import org.yeastrc.proteomics.fasta.FASTAEntry;
import org.yeastrc.proteomics.fasta.FASTAFileParser;
import org.yeastrc.proteomics.fasta.FASTAHeader;

import java.io.*;
import java.util.Collection;
import java.util.Map;

public class NRFilterer {

    /**
     * Filter the given nr FASTA file using the given input. Will output informational messages to
     * standard error. Will output the FASTA data to standard out.
     *
     * @param taxonomyIdsToKeep The taxonomy IDs on which we are filtering. Only accession strings that
     *                          map to any of these taxonomy ids or any of their descendants will be included.
     *                          E.g., including a taxonomy id of 2 (bacteria) will include all accession strings
     *                          that can be mapped to any bacteria taxonomies.
     * @param fastaFile The nr FASTA file (gzipped or uncompressed)
     * @param nodesFile The nodes.dmp file from the NCBI taxonomy download
     * @param accessionTaxonomyFile The prot.accession2taxid (gzipped or uncompressed) from NCBI.
     * @param databases The databases on which to also filter accession strings that will be included. Only
     *                  accession strings corresponding to one of these databases will be included.
     * @throws IOException
     */
    public static void filterNR(Integer[] taxonomyIdsToKeep,
                                File fastaFile,
                                File nodesFile,
                                File accessionTaxonomyFile,
                                String[] databases ) throws IOException {

        Collection<String> accessionStrings;

        {
            System.err.print("Reading in taxonomy hierarchy mapping...");
            Map<Integer, Integer> idParentIdMap = TaxonomyNodesReader.getIdParentIdMap(nodesFile);
            System.err.println(" Done.");

            System.err.println("Finding accession strings to include...");
            accessionStrings = (new AccessionTaxonomyIdReader()).getAccessionStringsToKeep(accessionTaxonomyFile, taxonomyIdsToKeep, idParentIdMap, databases);
            System.err.println("\nKeeping " + accessionStrings.size() + ".");
        }

        System.err.println( "Processing NR fasta file..." );
        processNRFile( fastaFile, accessionStrings );
    }

    /**
     * Filter and print the supplied nr FASTA file so that only accession strings in the given collection
     * are included in the output data. Will output informational messages to standard error. Will output
     * the FASTA data to standard out.
     *
     * @param fastaFile The nr FASTA file (gzipped or uncompressed)
     * @param accessionStrings The accession strings on which to filter.
     * @throws IOException
     */
    private static void processNRFile( File fastaFile,
                                       Collection<String> accessionStrings) throws IOException {

        try (FASTAFileParser fastaFileParser = FASTAUtils.getFastaFileParser(fastaFile)) {

            int totalEntriesParsed = 0;
            int totalEntriesKept = 0;

            FASTAEntry fastaEntry = fastaFileParser.getNextEntry();
            while( fastaEntry != null ) {

                totalEntriesParsed++;

                if( shouldKeepFASTAEntry( fastaEntry, accessionStrings ) ) {
                    totalEntriesKept++;
                    FASTAUtils.printFASTAEntryToStandardOut( fastaEntry, accessionStrings );
                }

                if( totalEntriesParsed % 1000000 == 0 ) {
                    System.err.print( "Parsed " + totalEntriesParsed + " FASTA entries. Kept " + totalEntriesKept + "\r" );
                }

                fastaEntry = fastaFileParser.getNextEntry();
            }

        }
    }

    /**
     * Determine whether or not a given FASTA entry should be kept based on the accession strings
     * contained in that FASTA entry.
     *
     * @param fastaEntry the FASTAEntry we're testing
     * @param accessionStrings The list of valid accession strings, in which at least one of the accession
     *                         strings of the FASTA entry must be found.
     * @return
     */
    private static boolean shouldKeepFASTAEntry( FASTAEntry fastaEntry,
                                                 Collection<String> accessionStrings ) {

        for( FASTAHeader header : fastaEntry.getHeaders() ) {
            if( accessionStrings.contains( header.getName() ) ) {
                return true;
            }
        }

        return false;
    }
}
