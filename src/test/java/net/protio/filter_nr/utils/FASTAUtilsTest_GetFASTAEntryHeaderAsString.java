package net.protio.filter_nr.utils;

import net.protio.filter_nr.constants.ProgramConstants;
import org.junit.Before;
import org.junit.Test;
import org.yeastrc.proteomics.fasta.FASTAEntry;
import org.yeastrc.proteomics.fasta.FASTAFileParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FASTAUtilsTest_GetFASTAEntryHeaderAsString {

    private FASTAEntry fastaEntry;

    private final String FASTAEntryString1 = ">foo1 description1" + ProgramConstants._CONTROL_A + "foo2 description2\n";
    private final String FASTAEntryString2 = ">foo2 description2" + ProgramConstants._CONTROL_A + "foo1 description1\n";

    @Before
    public void setUp() throws IOException {

        String FASTAEntryString = ">foo1 description1" + ProgramConstants._CONTROL_A + "foo2 description2\n";
        FASTAEntryString += "PEPTIDEPEPTIDEPEPTIDEPEPTIDEPEPEPEPEPEPPEPTIDE\n";

        fastaEntry = FASTAFileParserFactory.getInstance().getFASTAFileParser( new ByteArrayInputStream( FASTAEntryString.getBytes() ) ).getNextEntry();
    }

    @Test
    public void testWithNullFilters() {

        String calculatedHeader = FASTAUtils.getFASTAEntryHeaderAsString( fastaEntry, null );
        assertTrue( calculatedHeader.equals( FASTAEntryString1 ) || calculatedHeader.equals( FASTAEntryString2 ) );
    }

    @Test
    public void testWithEmptyFilters() {

        String calculatedHeader = FASTAUtils.getFASTAEntryHeaderAsString( fastaEntry, new HashSet<>());
        assertTrue( calculatedHeader.equals( FASTAEntryString1 ) || calculatedHeader.equals( FASTAEntryString2 ) );
    }

    @Test
    public void testWithOneFilter() {

        String calculatedHeader = FASTAUtils.getFASTAEntryHeaderAsString( fastaEntry, Arrays.asList( "foo2" ) );
        System.out.println( calculatedHeader );
        assertEquals( calculatedHeader, ">foo2 description2\n");
    }

    @Test
    public void testWithTwoFilters() {

        String calculatedHeader = FASTAUtils.getFASTAEntryHeaderAsString( fastaEntry, Arrays.asList( "foo1", "foo2" ) );
        assertTrue( calculatedHeader.equals( FASTAEntryString1 ) || calculatedHeader.equals( FASTAEntryString2 ) );
    }

}
