package net.protio.filter_nr.main;

import net.protio.filter_nr.utils.AccessionStringUtils;
import net.protio.filter_nr.utils.FASTAUtils;
import org.yeastrc.proteomics.fasta.FASTAEntry;
import org.yeastrc.proteomics.fasta.FASTAFileParser;
import org.yeastrc.proteomics.fasta.FASTAHeader;

import java.io.File;
import java.io.IOException;

public class NRAccessionTester {

    public static void testNRAccessions( File nrFASTAFile ) throws IOException {

        try (FASTAFileParser fastaFileParser = FASTAUtils.getFastaFileParser(nrFASTAFile)) {

            int totalEntriesParsed = 0;

            FASTAEntry fastaEntry = fastaFileParser.getNextEntry();
            while( fastaEntry != null ) {

                totalEntriesParsed++;

                for(FASTAHeader header : fastaEntry.getHeaders() ) {

                    if( AccessionStringUtils.getReferenceDatabaseIds( header.getName() ).size() < 1 ) {
                        System.out.println( "\nUnable to find database for accession: " + header.getName() );
                    }
                }

                if( totalEntriesParsed % 1000000 == 0 ) {
                    System.err.print( "Parsed " + totalEntriesParsed + " FASTA entries.\r" );
                }

                fastaEntry = fastaFileParser.getNextEntry();
            }

        }
    }
}
