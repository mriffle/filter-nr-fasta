package net.protio.filter_nr.readers;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TaxonomyNodesReader {

    /**
     * Process the nodes.dmp file and return a mapping of NCBI taxonomy ids to their parent ids
     *
     * @param taxonomyFile
     * @return
     * @throws IOException
     */
    public static Map<Integer,Integer> getIdParentIdMap( File taxonomyFile ) throws IOException {

        Map<Integer,Integer> idParentIdMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(taxonomyFile))) {

            for (String line = br.readLine(); line != null; line = br.readLine()) {

                String[] fields = line.split( "\t\\|\t" );

                int taxId = Integer.parseInt( fields[ 0 ] );
                int parentId = Integer.parseInt( fields[ 1 ] );

                idParentIdMap.put( taxId, parentId );
            }
        }

        return idParentIdMap;
    }

}
