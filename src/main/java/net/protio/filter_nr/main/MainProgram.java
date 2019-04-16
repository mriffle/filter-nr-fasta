package net.protio.filter_nr.main;

import net.protio.filter_nr.constants.ProgramConstants;
import picocli.CommandLine;

import java.io.File;

@CommandLine.Command(name = "java -jar " + ProgramConstants.CONVERTER_NAME,
        mixinStandardHelpOptions = true,
        version = ProgramConstants.CONVERTER_NAME + " " + ProgramConstants.PROGRAM_VERSION,
        sortOptions = false,
        synopsisHeading = "%n",
        descriptionHeading = "%n@|bold,underline Description:|@%n%n",
        optionListHeading = "%n@|bold,underline Options:|@%n"
)
public class MainProgram implements Runnable {

    @CommandLine.Option(names = { "-t", "--taxonomy-id" }, required = true, description = "Taxonomy ID to filter on. " +
            "Only nr entries with this id or any of its descendants will be output. May use parameter multiple times." )
    private Integer[] taxonomyIds;

    @CommandLine.Option(names = { "-d", "--database" }, description = "[Optional] The name of the databases to use, " +
            "can be used multiple times. Must be one of: uniprot, refseq, pdb, genbank. Default is to include all." )
    private String[] databases;

    @CommandLine.Option(names = { "-n", "--nodes" }, required = true, description = "Path to NCBI Taxonomy nodes file (nodes.dmp " +
            "from ftp://ftp.ncbi.nih.gov/pub/taxonomy/new_taxdump/new_taxdump.tar.gz)" )
    private File nodesFile;

    @CommandLine.Option(names = { "-a", "--accession" }, required = true, description = "Path to accession string to taxonomy id " +
            "mapping. Can be gzipped or uncompressed.  (ftp://ftp.ncbi.nih.gov/pub/taxonomy/accession2taxid/prot.accession2taxid.gz)" )
    private File accessionTaxonomyFile;

    @CommandLine.Option(names = { "-f", "--nr-fasta" }, required = true, description = "Path to NCBI nr file. Can be gzipped " +
            "or uncompressed. (ftp://ftp.ncbi.nlm.nih.gov/blast/db/FASTA/nr.gz)" )
    private File fastaFile;

    @CommandLine.Option(names = { "--test-lookups" }, required = false, description = "If this parameter is present, " +
            "print all accession strings in the nr FASTA that could NOT be mapped to a database (e.g. refseq or uniprot)." +
            "If nothing is printed, everything is found." )
    private boolean testAccessionLookups;


    public void run()  {

        try {

            if( testAccessionLookups ) {
                NRAccessionTester.testNRAccessions( fastaFile );
            } else{
                NRFilterer.filterNR(taxonomyIds, fastaFile, nodesFile, accessionTaxonomyFile, databases);
            }
        } catch( Throwable t ) {
            System.err.println( "\n\nEncountered an error during filtering:" );
            System.err.println( t.getMessage() );

            t.printStackTrace();

            System.exit( 1 );

        }

    }

    public static void main( String[] args ) {

        CommandLine.run(new MainProgram(), args);

    }
}
