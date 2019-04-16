package net.protio.filter_nr.utils;

import net.protio.filter_nr.constants.ProgramConstants;
import org.yeastrc.proteomics.fasta.FASTAEntry;
import org.yeastrc.proteomics.fasta.FASTAFileParser;
import org.yeastrc.proteomics.fasta.FASTAFileParserFactory;
import org.yeastrc.proteomics.fasta.FASTAHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.zip.GZIPInputStream;

public class FASTAUtils {

    /**
     * Create a FASTA header line ">name descriptionCONTROLAname description..." from the given FASTAEntry
     * object.
     *
     * @param fastaEntry The FASTAEntry to process
     * @param accessionStrings The accession strings on which to filter--only accession strings in this
     *                         collection will be included.
     * @return
     */
    public static String getFASTAEntryHeaderAsString( FASTAEntry fastaEntry, Collection<String> accessionStrings ) {

        StringBuilder sb = new StringBuilder();

        sb.append( ">" );

        boolean first = true;
        for(FASTAHeader header : fastaEntry.getHeaders() ) {

            if( accessionStrings == null || accessionStrings.size() < 1 || accessionStrings.contains( header.getName() ) ) {

                if (!first) {
                    sb.append(ProgramConstants._CONTROL_A);
                } else {
                    first = false;
                }

                sb.append(header.getLine());
            }
        }

        sb.append( "\n" );

        return sb.toString();
    }

    /**
     * Print the given FASTAEntry to standard out in FASTA format:
     * >name description
     * sequence
     *
     * @param fastaEntry
     * @param accessionStrings
     */
    public static void printFASTAEntryToStandardOut(FASTAEntry fastaEntry, Collection<String> accessionStrings) {
        System.out.print( getFASTAEntryHeaderAsString( fastaEntry, accessionStrings ) );
        System.out.println( fastaEntry.getSequence() );
    }

    /**
     * Get a FASTAFileParser for the given FASTA file. File must be either gzipped (end with .gz) or
     * uncompressed.
     *
     * @param fastaFile
     * @return
     * @throws IOException
     */
    public static FASTAFileParser getFastaFileParser(File fastaFile) throws IOException {

        InputStream is = null;

        if( fastaFile.getName().endsWith( ".gz" ) ) {
            is =  new GZIPInputStream( new FileInputStream( fastaFile ) );
        } else {
            is = new FileInputStream( fastaFile );
        }

        return FASTAFileParserFactory.getInstance().getFASTAFileParser( is );
    }
}
