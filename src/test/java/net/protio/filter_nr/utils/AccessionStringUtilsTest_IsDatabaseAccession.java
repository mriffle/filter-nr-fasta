package net.protio.filter_nr.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccessionStringUtilsTest_IsDatabaseAccession {

    @Test
    public void testEmptyString() {
        assertFalse( AccessionStringUtils.isDatabaseAccession( "",  1 ) );
    }

    @Test
    public void testNullString() {
        assertFalse( AccessionStringUtils.isDatabaseAccession( null,  1 ) );
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidDatabase() {
        AccessionStringUtils.isDatabaseAccession( null,  1000 );
    }

    @Test
    public void testValidRefseq() {
        assertTrue( AccessionStringUtils.isDatabaseAccession( "WP_003251213.1",  AccessionStringUtils.DATABASE_REFSEQ ) );
    }
    @Test
    public void testInvalidRefseq() {
        assertFalse( AccessionStringUtils.isDatabaseAccession( "foo",  AccessionStringUtils.DATABASE_REFSEQ ) );
    }

    @Test
    public void testValidUniprot() {
        assertTrue( AccessionStringUtils.isDatabaseAccession( "Q02VU1.1",  AccessionStringUtils.DATABASE_UNIPROT ) );
    }
    @Test
    public void testInvalidUniprot() {
        assertFalse( AccessionStringUtils.isDatabaseAccession( "foo",  AccessionStringUtils.DATABASE_UNIPROT ) );
    }

    @Test
    public void testValidPdb() {
        assertTrue( AccessionStringUtils.isDatabaseAccession( "2I2T_O",  AccessionStringUtils.DATABASE_PDB ) );
    }
    @Test
    public void testInvalidPdb() {
        assertFalse( AccessionStringUtils.isDatabaseAccession( "foo",  AccessionStringUtils.DATABASE_PDB ) );
    }

    @Test
    public void testValidPir() {
        assertTrue( AccessionStringUtils.isDatabaseAccession( "pir||T49736",  AccessionStringUtils.DATABASE_PIR ) );
    }
    @Test
    public void testInvalidPir() {
        assertFalse( AccessionStringUtils.isDatabaseAccession( "foo",  AccessionStringUtils.DATABASE_PIR ) );
    }

    @Test
    public void testValidPrf() {
        assertTrue( AccessionStringUtils.isDatabaseAccession( "prf||2209341B",  AccessionStringUtils.DATABASE_PRF ) );
    }
    @Test
    public void testInvalidPrf() {
        assertFalse( AccessionStringUtils.isDatabaseAccession( "foo",  AccessionStringUtils.DATABASE_PRF ) );
    }

    @Test
    public void testValidGenbank() {
        assertTrue( AccessionStringUtils.isDatabaseAccession( "ABB63458.1",  AccessionStringUtils.DATABASE_GENBANK ) );
    }
    @Test
    public void testInvalidGenbank() {
        assertFalse( AccessionStringUtils.isDatabaseAccession( "foo",  AccessionStringUtils.DATABASE_GENBANK ) );
    }

}
