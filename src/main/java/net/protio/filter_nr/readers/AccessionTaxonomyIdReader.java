package net.protio.filter_nr.readers;

import net.protio.filter_nr.utils.AccessionStringUtils;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class AccessionTaxonomyIdReader {

    /**
     * Process the supplied prot.accession2taxid.gz file (can be gzipped or uncompressed) from NCBI and return
     * the collection of accession strings we will keep based on the taxonomy and database filters.
     *
     * @param accessionTaxonomyMapFile The file containing the mapping of accession strings to NCBI taxonomy ids
     * @param taxonomyIdsToKeep The taxonomy ids on whcih we are filtering
     * @param idParentIdMap The map of taxonomy ids to parent ids
     * @param databases The databases on wchih we are filtering (e.g. "refseq" or "uniprot". May be null
     * @return
     * @throws IOException
     */
    public Collection<String> getAccessionStringsToKeep(File accessionTaxonomyMapFile,
                                                             Integer[] taxonomyIdsToKeep,
                                                             Map<Integer, Integer> idParentIdMap,
                                                             String[] databases  ) throws IOException {

        Collection<String> accessionsToKeep = new HashSet<>();

        InputStream is = null;

        try {

            if (accessionTaxonomyMapFile.getName().endsWith("gz")) {
                is = new GZIPInputStream(new FileInputStream(accessionTaxonomyMapFile));
            } else {
                is = new FileInputStream(accessionTaxonomyMapFile);
            }

            int linesProcessed = 0;
            int accessionsKept = 0;

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

                br.readLine();      // throw away header line

                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    String[] fields = line.split("\t");

                    // if we're filtering on database, don't include invalid accessions
                    if( databases == null || databases.length < 1 || AccessionStringUtils.accessionStringIncludedInDatabases( fields[ 1 ], databases ) ) {

                        if (shouldKeepAccessionTaxonomyId(Integer.parseInt(fields[2]), taxonomyIdsToKeep, idParentIdMap)) {
                            accessionsToKeep.add( fields[1] );
                            accessionsKept++;
                        }
                    }

                    linesProcessed++;
                    if( linesProcessed % 1000000 == 0 ) {
                        System.err.print( "Processed " + linesProcessed + " lines (Kept " + accessionsKept + ")\r" );
                    }

                }
            }

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {

                }
            }
        }

        return accessionsToKeep;
    }

    /**
     * Given the one of the taxonomy ids we're filtering on, determine if the given taxonomy id (corresponding to
     * an accession/taxonomy id mapping from NCBI) should be kept.
     * @param accessionTaxonomyId The taxonomy id we are testing
     * @param taxonomyIdToKeep A taxonomy id on which we are filtering
     * @param idParentIdMap The map of taxonomy ids to parent ids
     * @return
     */
    private boolean shouldKeepAccessionTaxonomyId( Integer accessionTaxonomyId,
                                                           Integer taxonomyIdToKeep,
                                                           Map<Integer, Integer> idParentIdMap ) {

        int taxonomyIdToTest = accessionTaxonomyId;

        while( taxonomyIdToTest != 1 && taxonomyIdToTest != 0  ) {

            if( taxonomyIdToTest == taxonomyIdToKeep ) {
                return true;
            }

            if( idParentIdMap.containsKey( taxonomyIdToTest ) ) {
                taxonomyIdToTest = idParentIdMap.get( taxonomyIdToTest );
            } else {
                break;  // could not find a parent
            }
        }

        return false;
    }

    /**
     * Given the taxonomy ids we're filtering on, determine if the given taxonomy id (corresponding to
     * an accession/taxonomy id mapping from NCBI) should be kept.
     *
     * @param accessionTaxonomyId The taxonomy id we are testing
     * @param taxonomyIdsToKeep The taxonomy ids on which we are filtering
     * @param idParentIdMap The map of taxonomy ids to parent ids
     * @return
     */
    private boolean shouldKeepAccessionTaxonomyId( Integer accessionTaxonomyId,
                                                           Integer[] taxonomyIdsToKeep,
                                                           Map<Integer, Integer> idParentIdMap ) {

        for( Integer taxonomyIdToKeep : taxonomyIdsToKeep ) {

            if( shouldKeepAccessionTaxonomyId( accessionTaxonomyId, taxonomyIdToKeep, idParentIdMap ) ) {
                return true;
            }

        }

        return false;
    }
}
