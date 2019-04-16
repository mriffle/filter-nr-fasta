package net.protio.filter_nr.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccessionStringUtilsTest_GetDatabaseIdForString {

    @Test(expected = RuntimeException.class)
    public void testEmptyString() {
        AccessionStringUtils.getDatabaseIdForString("");
    }

    @Test(expected = RuntimeException.class)
    public void testNullString() {
        AccessionStringUtils.getDatabaseIdForString(null);
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidString() {
        AccessionStringUtils.getDatabaseIdForString("dkdleldkei");
    }

    @Test
    public void testUniprot() {
        assertEquals(AccessionStringUtils.DATABASE_UNIPROT, AccessionStringUtils.getDatabaseIdForString("uniprot"));
    }

    @Test
    public void testGenbank() {
        assertEquals(AccessionStringUtils.DATABASE_GENBANK, AccessionStringUtils.getDatabaseIdForString("genbank"));
    }

    @Test
    public void testRefseq() {
        assertEquals(AccessionStringUtils.DATABASE_REFSEQ, AccessionStringUtils.getDatabaseIdForString("refseq"));
    }

    @Test
    public void testPdb() {
        assertEquals(AccessionStringUtils.DATABASE_PDB, AccessionStringUtils.getDatabaseIdForString("pdb"));
    }

    @Test
    public void testPir() {
        assertEquals(AccessionStringUtils.DATABASE_PIR, AccessionStringUtils.getDatabaseIdForString("pir"));
    }

    @Test
    public void testPrf() {
        assertEquals(AccessionStringUtils.DATABASE_PRF, AccessionStringUtils.getDatabaseIdForString("prf"));
    }

}

