package mage.client.util.sets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.constants.SetType;

/**
 * Utility class for constructed formats (expansions and other editions).
 *
 * @author nantuko
 */
public class ConstructedFormats {

    private static final GregorianCalendar calendar = new GregorianCalendar();

    public static final String ALL = "- All Sets";
    public static final String STANDARD = "- Standard";
    public static final String EXTENDED = "- Extended";
    public static final String MODERN = "- Modern";
    
    private static final Map<String, List<String>> underlyingSetCodesPerFormat = new HashMap<>();
    private static final List<String> formats = new ArrayList<String>();

    private ConstructedFormats() {
    }

    public static String[] getTypes() {
        return formats.toArray(new String[0]);
    }

    public static String getDefault() {
        return STANDARD;
    }
    
    public static List<String> getSetsByFormat(final String format) {
    	if(!format.equals(ALL)) {
    		return underlyingSetCodesPerFormat.get(format);
    	}
        return all;

    }

    private static void buildLists() {
        GregorianCalendar cutoff;
        // month is zero based so January = 0
        if (calendar.get(Calendar.MONTH) > 8) {
            cutoff = new GregorianCalendar(calendar.get(Calendar.YEAR) - 1, Calendar.SEPTEMBER, 1);
        }
        else {
            cutoff = new GregorianCalendar(calendar.get(Calendar.YEAR) - 2, Calendar.SEPTEMBER, 1);
        }
        final Map<String, ExpansionInfo> expansionInfo = new HashMap<String, ExpansionInfo>();
        for (ExpansionInfo set : ExpansionRepository.instance.getAll()) {
        	expansionInfo.put(set.getName(), set);
        	formats.add(set.getName());
            if (set.getType().equals(SetType.CORE) || set.getType().equals(SetType.EXPANSION)) {
                if (set.getReleaseDate().after(cutoff.getTime())) {
                    if(underlyingSetCodesPerFormat.get(STANDARD) == null) {
                    	underlyingSetCodesPerFormat.put(STANDARD, new ArrayList<String>());
                    }
                    underlyingSetCodesPerFormat.get(STANDARD).add(set.getCode());
                }
                if (set.getReleaseDate().after(extendedDate)) {
                    if(underlyingSetCodesPerFormat.get(EXTENDED) == null) {
                    	underlyingSetCodesPerFormat.put(EXTENDED, new ArrayList<String>());
                    }
                    underlyingSetCodesPerFormat.get(EXTENDED).add(set.getCode());
                }
                if (set.getReleaseDate().after(modernDate)) {
                    if(underlyingSetCodesPerFormat.get(MODERN) == null) {
                    	underlyingSetCodesPerFormat.put(MODERN, new ArrayList<String>());
                    }
                    underlyingSetCodesPerFormat.get(MODERN).add(set.getCode());
                }
            }
            
            if(underlyingSetCodesPerFormat.get(set.getName()) == null) {
            	underlyingSetCodesPerFormat.put(set.getName(), new ArrayList<String>());
            }
            
            underlyingSetCodesPerFormat.get(set.getName()).add(set.getCode());
            
            if(set.getType().equals(SetType.EXPANSION) && set.getBlockName() != null) {
            	String blockDisplayName = getBlockDisplayName(set.getBlockName());
            	if(underlyingSetCodesPerFormat.get(blockDisplayName) == null) {
            		underlyingSetCodesPerFormat.put(blockDisplayName, new ArrayList<String>());
            	}

            	underlyingSetCodesPerFormat.get(blockDisplayName).add(set.getCode());
            	
            	if(expansionInfo.get(blockDisplayName) == null) {
                	expansionInfo.put(blockDisplayName, set);
                	formats.add(blockDisplayName);
            	}
            	
            	if(expansionInfo.get(blockDisplayName).getReleaseDate().after(set.getReleaseDate())) {
            		expansionInfo.put(blockDisplayName, set);
            	}

            }
            
            if(set.getType().equals(SetType.SUPPLEMENTAL) && set.getBlockName() != null) {
            	if(expansionInfo.get(set.getBlockName()) == null) {
                	expansionInfo.put(set.getBlockName(), set);
            	}
            	
            	if(expansionInfo.get(set.getBlockName()).getReleaseDate().before(set.getReleaseDate())) {
            		expansionInfo.put(set.getBlockName(), set);
            	}
            }
        }
        
        Collections.sort(formats, new Comparator<String>() {

			@Override
			public int compare(String name1, String name2) {
				ExpansionInfo expansionInfo1 = expansionInfo.get(name1);
				ExpansionInfo expansionInfo2 = expansionInfo.get(name2);
				
				if(expansionInfo1.getType().compareTo(expansionInfo2.getType()) == 0) {
					SetType setType = expansionInfo1.getType();
					if(setType.equals(SetType.EXPANSION)) {
						
						if(expansionInfo1.getBlockName() == null) {
							if(expansionInfo2.getBlockName() == null) {
								return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
							}
							
							return 1;
						}
						
						if(expansionInfo2.getBlockName() == null) {
							return -1;
						}
						
						//Block comparison
						if(name1.endsWith("Block") && name2.endsWith("Block")) {
							return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
        				}
						
						if(name1.endsWith("Block")) {
							if(expansionInfo1.getBlockName().equals(expansionInfo2.getBlockName())) {
								return -1;
							}
						}
						
						if(name2.endsWith("Block")) {
							if(expansionInfo1.getBlockName().equals(expansionInfo2.getBlockName())) {
								return 1;
							}
						}
						
						return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
						
					} else if(setType.equals(SetType.SUPPLEMENTAL)) {
						if(expansionInfo1.getBlockName() == null) {
							if(expansionInfo2.getBlockName() == null) {
								return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
							}
							
							return -1;
						}
						
						if(expansionInfo2.getBlockName() == null) {
							return 1;
						}
						
						if(expansionInfo1.getBlockName().equals(expansionInfo2.getBlockName())) {
							//If release date is the same, sort alphabetically.
							if(expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate()) == 0) {
								return name1.compareTo(name2);
							}
							return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
						}
						
						if(expansionInfo1.getBlockName().startsWith("Duel Decks")) {
							if(expansionInfo1.getBlockName().startsWith("Duel Decks: Anthology")) {
								return 1;
							}
							return 1;
						}
						if(expansionInfo2.getBlockName().startsWith("Duel Decks")) {
							return -1;
						}
						
						ExpansionInfo blockInfo1 = expansionInfo.get(expansionInfo1.getBlockName());
						ExpansionInfo blockInfo2 = expansionInfo.get(expansionInfo2.getBlockName());
						
						return blockInfo2.getReleaseDate().compareTo(blockInfo1.getReleaseDate());
						
						
					} else {
						return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
					}
				}
				return expansionInfo1.getType().compareTo(expansionInfo2.getType());
			}
        	
		});

        formats.add(0, MODERN);
        formats.add(0, EXTENDED);
        formats.add(0, STANDARD);
        formats.add(0, ALL);
    }
    
    private static String getBlockDisplayName(String blockName) {
    	StringBuilder builder = new StringBuilder();
    	builder.append("* ").append(blockName).append(" Block");
    	
    	return builder.toString();
    }

    
    private static final Date extendedDate = new GregorianCalendar(2009, 8, 20).getTime();

    private static final Date modernDate = new GregorianCalendar(2003, 7, 20).getTime();

    // for all sets just return empty list
    private static final List<String> all = new ArrayList<>();

    static {
        buildLists();
    }
}
