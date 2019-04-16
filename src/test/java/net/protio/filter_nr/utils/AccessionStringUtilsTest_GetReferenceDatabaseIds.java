package net.protio.filter_nr.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class AccessionStringUtilsTest_GetReferenceDatabaseIds {

    private static final Collection<Integer> EMPTY_SET =  new HashSet<>();
    private static final Collection<Integer> UNIPROT_SET =  new HashSet<Integer>(Arrays.asList( AccessionStringUtils.DATABASE_UNIPROT ));
    private static final Collection<Integer> REFSEQ_SET =  new HashSet<Integer>(Arrays.asList( AccessionStringUtils.DATABASE_REFSEQ));
    private static final Collection<Integer> PIR_SET =  new HashSet<Integer>(Arrays.asList( AccessionStringUtils.DATABASE_PIR ));
    private static final Collection<Integer> PRF_SET =  new HashSet<Integer>(Arrays.asList( AccessionStringUtils.DATABASE_PRF ));
    private static final Collection<Integer> PDB_SET =  new HashSet<Integer>(Arrays.asList( AccessionStringUtils.DATABASE_PDB ));
    private static final Collection<Integer> GENBANK_SET =  new HashSet<Integer>(Arrays.asList( AccessionStringUtils.DATABASE_GENBANK ));

    @Test
    public void testEmptyString() {
        assertEquals(EMPTY_SET, AccessionStringUtils.getReferenceDatabaseIds( "" ) );
    }

    @Test
    public void testNullInput() {
        assertEquals(EMPTY_SET, AccessionStringUtils.getReferenceDatabaseIds( null ) );
    }


    /************ Some uniprot tests ************/
    @Test
    public void testUniprot() {
        assertEquals(UNIPROT_SET, AccessionStringUtils.getReferenceDatabaseIds( "Q02VU1.1" ) );
    }
    @Test
    public void testUniprot2() {
        assertEquals(UNIPROT_SET, AccessionStringUtils.getReferenceDatabaseIds( "P0A7U1.2" ) );
    }
    @Test
    public void testUniprot3() {
        assertEquals(UNIPROT_SET, AccessionStringUtils.getReferenceDatabaseIds( "B4T3F3.1" ) );
    }
    @Test
    public void testUniprot4() {
        assertEquals(UNIPROT_SET, AccessionStringUtils.getReferenceDatabaseIds( "C0Q6G0" ) );
    }

    /************ Some refseq tests ************/
    @Test
    public void testRefseq() {
        assertEquals(REFSEQ_SET, AccessionStringUtils.getReferenceDatabaseIds( "WP_003251213.1" ) );
    }
    @Test
    public void testRefseq2() {
        assertEquals(REFSEQ_SET, AccessionStringUtils.getReferenceDatabaseIds( "NP_746135.5" ) );
    }
    @Test
    public void testRefseq3() {
        assertEquals(REFSEQ_SET, AccessionStringUtils.getReferenceDatabaseIds( "NP_746135" ) );
    }


    /************ Some pdb tests ************/
    @Test
    public void testPdbseq() {
        assertEquals(PDB_SET, AccessionStringUtils.getReferenceDatabaseIds( "2I2T_O" ) );
    }
    @Test
    public void testPdbse2() {
        assertEquals(PDB_SET, AccessionStringUtils.getReferenceDatabaseIds( "2I2T_OA" ) );
    }
    @Test
    public void testPdbseq3() {
        assertEquals(PDB_SET, AccessionStringUtils.getReferenceDatabaseIds( "2I2T" ) );
    }

    /************ Some genbank tests ************/
    @Test
    public void testGenbankSeq() {
        assertEquals(GENBANK_SET, AccessionStringUtils.getReferenceDatabaseIds( "ABB63458.1" ) );
    }
    @Test
    public void testGenbankSeq2() {
        assertEquals(GENBANK_SET, AccessionStringUtils.getReferenceDatabaseIds( "EGU95716" ) );
    }

    /************ Some pir tests ************/
    @Test
    public void testPirSeq() {
        assertEquals(PIR_SET, AccessionStringUtils.getReferenceDatabaseIds( "pir||T49736" ) );
    }

    /************ Some prf tests ************/
    @Test
    public void testPrfSeq() {
        assertEquals(PRF_SET, AccessionStringUtils.getReferenceDatabaseIds( "prf||2209341B" ) );
    }

}
