package net.protio.filter_nr.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccesionSringUtilsTest_AccessionStringIncludedInDatabase {

    @Test
    public void testEmptyString() {
        assertFalse( AccessionStringUtils.accessionStringIncludedInDatabases( "", new String[]{ "refseq" } ) );
    }

    @Test(expected = RuntimeException.class)
    public void testNullString() {
        assertFalse( AccessionStringUtils.accessionStringIncludedInDatabases( "", new String[]{ null } ) );
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidDatabaseName() {
        AccessionStringUtils.accessionStringIncludedInDatabases( "", new String[]{ "foo" } );
    }

    @Test
    public void testNullDatabaseList() {
        assertTrue( AccessionStringUtils.accessionStringIncludedInDatabases( "", null ) );
    }

    @Test
    public void testEmptyDatabaseList() {
        assertTrue( AccessionStringUtils.accessionStringIncludedInDatabases( "",  new String[]{ }) );
    }

    @Test
    public void testValidRefseq() {
        assertTrue( AccessionStringUtils.accessionStringIncludedInDatabases( "WP_003251213.1",  new String[]{ "refseq" }) );
    }

    @Test
    public void testValidRefseq_EmptyDatabaseList() {
        assertTrue( AccessionStringUtils.accessionStringIncludedInDatabases( "WP_003251213.1",  new String[]{ }) );
    }

    @Test
    public void testValidRefseqOrUniprot() {
        assertTrue( AccessionStringUtils.accessionStringIncludedInDatabases( "WP_003251213.1",  new String[]{ "refseq", "uniprot" }) );
    }

    @Test
    public void testInvValidRefseqOrUniprot() {
        assertFalse( AccessionStringUtils.accessionStringIncludedInDatabases( "2I2T_OA",  new String[]{ "refseq", "uniprot" }) );
    }

    @Test
    public void testValidRefseqIsUniprotOrPdb() {
        assertFalse( AccessionStringUtils.accessionStringIncludedInDatabases( "WP_003251213.1",  new String[]{ "pdb", "uniprot" }) );
    }


}
