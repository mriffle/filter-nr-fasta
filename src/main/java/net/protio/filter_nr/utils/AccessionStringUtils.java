package net.protio.filter_nr.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

public class AccessionStringUtils {

    public static final int DATABASE_UNIPROT = 0;
    public static final int DATABASE_GENBANK = 1;
    public static final int DATABASE_REFSEQ = 2;
    public static final int DATABASE_PDB = 3;
    public static final int DATABASE_PIR = 4;
    public static final int DATABASE_PRF = 5;

    private static final Map<String, Integer> DATABASE_LOOKUP_MAP;

    static {
        DATABASE_LOOKUP_MAP = new HashMap<>();

        DATABASE_LOOKUP_MAP.put( "uniprot", DATABASE_UNIPROT );
        DATABASE_LOOKUP_MAP.put( "genbank", DATABASE_GENBANK );
        DATABASE_LOOKUP_MAP.put( "refseq", DATABASE_REFSEQ );
        DATABASE_LOOKUP_MAP.put( "pdb", DATABASE_PDB );
        DATABASE_LOOKUP_MAP.put( "pir", DATABASE_PIR );
        DATABASE_LOOKUP_MAP.put( "prf", DATABASE_PRF );
    }

    public static int getDatabaseIdForString( String accession ) {
        if( DATABASE_LOOKUP_MAP.containsKey( accession ) ) {
            return DATABASE_LOOKUP_MAP.get( accession );
        }
        throw new RuntimeException( "Unsupported database name: " + accession );
    }

    public static Collection<Integer> getReferenceDatabaseIds(String accession ) {

        Collection<Integer> referenceDatabaseIds = new HashSet<>();

        if( isGenBankAccession( accession ) ) {
            referenceDatabaseIds.add( DATABASE_GENBANK );
        }

        if( isPDBAccession( accession ) ) {
            referenceDatabaseIds.add( DATABASE_PDB );
        }

        if( isUniprotAccession( accession ) ) {
            referenceDatabaseIds.add( DATABASE_UNIPROT );
        }

        if( isRefseqAccession( accession ) ) {
            referenceDatabaseIds.add( DATABASE_REFSEQ );
        }

        if( isPIRAccession( accession ) ) {
            referenceDatabaseIds.add( DATABASE_PIR );
        }

        if( isPRFAccession( accession ) ) {
            referenceDatabaseIds.add( DATABASE_PRF );
        }

        return referenceDatabaseIds;
    }

    public static boolean accessionStringIncludedInDatabases( String accession, String[] databases ) {
        if( databases == null || databases.length < 1 ) { return true; }

        for( String database : databases ) {
            if(AccessionStringUtils.isDatabaseAccession( accession, AccessionStringUtils.getDatabaseIdForString( database ) ) ) {
                return true;
            }
        }

        return false;
    }

    public static boolean isDatabaseAccession( String accession, int databaseId ) {

        if( databaseId == DATABASE_GENBANK ) {
            return isGenBankAccession( accession );
        }

        if( databaseId == DATABASE_PDB ) {
            return isPDBAccession( accession );
        }

        if( databaseId == DATABASE_REFSEQ ) {
            return isRefseqAccession( accession );
        }

        if( databaseId == DATABASE_UNIPROT ) {
            return isUniprotAccession( accession );
        }

        if( databaseId == DATABASE_PRF ) {
            return isPRFAccession( accession );
        }

        if( databaseId == DATABASE_PIR ) {
            return isPIRAccession( accession );
        }

        throw new RuntimeException( "Invalid database id: " + databaseId );
    }

    private static final Pattern GEN_PEP_PATTERN = Pattern.compile( "^\\w{3}\\d{5}(\\.\\d+)?$" );
    private static boolean isGenBankAccession( String accession ) {
        if( accession == null ) return false;
        return GEN_PEP_PATTERN.matcher( accession ).matches();
    }

    private static final Pattern UNIPROT_PATTERN = Pattern.compile( "^([A-N,R-Z][0-9]([A-Z][A-Z, 0-9][A-Z, 0-9][0-9]){1,2})(\\.\\d+)?|([O,P,Q][0-9][A-Z, 0-9][A-Z, 0-9][A-Z, 0-9][0-9])(\\.\\d+)?$" );
    private static boolean isUniprotAccession( String accession ) {
        if( accession == null ) return false;
        return UNIPROT_PATTERN.matcher( accession ).matches();
    }

    private static final Pattern REFSEQ_PATTERN = Pattern.compile( "^((AC|AP|NC|NG|NM|NP|NR|NT|NW|WP|XM|XP|XR|YP|ZP)_\\d+|(NZ\\_[A-Z]{4}\\d+))(\\.\\d+)?$" );
    private static boolean isRefseqAccession( String accession ) {
        if( accession == null ) return false;
        return REFSEQ_PATTERN.matcher( accession ).matches();
    }

    private static final Pattern PDB_PATTERN = Pattern.compile( "^[0-9][A-Za-z0-9]{3}(_\\w+)*$" );
    private static boolean isPDBAccession( String accession ) {
        if( accession == null ) return false;
        return PDB_PATTERN.matcher( accession ).matches();
    }

    private static boolean isPIRAccession( String accession ) {
        if( accession == null ) return false;
        return accession.startsWith( "pir" );
    }

    private static boolean isPRFAccession( String accession ) {
        if( accession == null ) return false;
        return accession.startsWith( "prf" );
    }

}
