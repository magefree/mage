package mage.client.util.sets;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mage.cards.repository.ExpansionInfo;
import mage.cards.repository.ExpansionRepository;
import mage.constants.SetType;
import mage.deck.Standard;

/**
 * Utility class for constructed formats (expansions and other editions).
 *
 * @author nantuko
 */
public final class ConstructedFormats {

    public static final String ALL = "- All Sets";
    public static final String STANDARD = "- Standard";
    public static final String EXTENDED = "- Extended";
    public static final String FRONTIER = "- Frontier";
    public static final String MODERN = "- Modern";
    public static final String VINTAGE_LEGACY = "- Vintage / Legacy";
    public static final String CUSTOM = "- Custom";
    public static final Standard STANDARD_CARDS = new Standard();

    private static final Map<String, List<String>> underlyingSetCodesPerFormat = new HashMap<>();
    private static final List<String> formats = new ArrayList<>();

    private ConstructedFormats() {
    }

    public static String[] getTypes() {
        return formats.toArray(new String[0]);
    }

    public static String getDefault() {
        return STANDARD;
    }

    public static List<String> getSetsByFormat(final String format) {
        if (!format.equals(ALL)) {
            return underlyingSetCodesPerFormat.get(format);
        }
        return all;

    }

    public static void ensureLists() {
        if (underlyingSetCodesPerFormat.isEmpty()) {
            buildLists();
        }
    }

    private static void buildLists() {
        underlyingSetCodesPerFormat.put(STANDARD, new ArrayList<>());
        underlyingSetCodesPerFormat.put(EXTENDED, new ArrayList<>());
        underlyingSetCodesPerFormat.put(FRONTIER, new ArrayList<>());
        underlyingSetCodesPerFormat.put(MODERN, new ArrayList<>());
        underlyingSetCodesPerFormat.put(VINTAGE_LEGACY, new ArrayList<>());
        underlyingSetCodesPerFormat.put(CUSTOM, new ArrayList<>());
        final Map<String, ExpansionInfo> expansionInfo = new HashMap<>();
        formats.clear(); // prevent NPE on sorting if this is not the first try
        for (ExpansionInfo set : ExpansionRepository.instance.getAll()) {
            expansionInfo.put(set.getName(), set);
            formats.add(set.getName());

            underlyingSetCodesPerFormat.put(set.getName(), new ArrayList<>());
            underlyingSetCodesPerFormat.get(set.getName()).add(set.getCode());

            // create the play formats
            if (set.getType() == SetType.CUSTOM_SET) {
                underlyingSetCodesPerFormat.get(CUSTOM).add(set.getCode());
                continue;
            }
            underlyingSetCodesPerFormat.get(VINTAGE_LEGACY).add(set.getCode());
            if (set.getType() == SetType.CORE || set.getType() == SetType.EXPANSION || set.getType() == SetType.SUPPLEMENTAL_STANDARD_LEGAL) {
                if (STANDARD_CARDS.getSetCodes().contains(set.getCode())) {
                    underlyingSetCodesPerFormat.get(STANDARD).add(set.getCode());
                }
                if (set.getType() != SetType.SUPPLEMENTAL_STANDARD_LEGAL) {
                    if (set.getReleaseDate().after(extendedDate) && (set.getType() == SetType.EXPANSION || set.getType() == SetType.CORE)) {
                        underlyingSetCodesPerFormat.get(EXTENDED).add(set.getCode());
                    }
                    if (set.getReleaseDate().after(frontierDate) && (set.getType() == SetType.EXPANSION || set.getType() == SetType.CORE)) {
                        underlyingSetCodesPerFormat.get(FRONTIER).add(set.getCode());
                    }
                    if (set.getReleaseDate().after(modernDate) && (set.getType() == SetType.EXPANSION || set.getType() == SetType.CORE)) {
                        underlyingSetCodesPerFormat.get(MODERN).add(set.getCode());
                    }
                }
            }

            // Create the Block formats
            if (set.getType() == SetType.EXPANSION && set.getBlockName() != null) {
                String blockDisplayName = getBlockDisplayName(set.getBlockName());
                underlyingSetCodesPerFormat.computeIfAbsent(blockDisplayName, k -> new ArrayList<>());

                underlyingSetCodesPerFormat.get(blockDisplayName).add(set.getCode());

                if (expansionInfo.get(blockDisplayName) == null) {
                    expansionInfo.put(blockDisplayName, set);
                    formats.add(blockDisplayName);
                }

                if (expansionInfo.get(blockDisplayName).getReleaseDate().after(set.getReleaseDate())) {
                    expansionInfo.put(blockDisplayName, set);
                }

            }

            if (set.getType() == SetType.SUPPLEMENTAL && set.getBlockName() != null) {
                expansionInfo.putIfAbsent(set.getBlockName(), set);

                if (expansionInfo.get(set.getBlockName()).getReleaseDate().before(set.getReleaseDate())) {
                    expansionInfo.put(set.getBlockName(), set);
                }
            }
        }

        formats.sort((name1, name2) -> {
            ExpansionInfo expansionInfo1 = expansionInfo.get(name1);
            ExpansionInfo expansionInfo2 = expansionInfo.get(name2);

            if (expansionInfo1.getType().compareTo(expansionInfo2.getType()) == 0) {
                SetType setType = expansionInfo1.getType();
                switch (setType) {
                    case EXPANSION:
                        if (expansionInfo1.getBlockName() == null) {
                            if (expansionInfo2.getBlockName() == null) {
                                return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
                            }

                            return 1;
                        }

                        if (expansionInfo2.getBlockName() == null) {
                            return -1;
                        }

                        //Block comparison
                        if (name1.endsWith("Block") && name2.endsWith("Block")) {
                            return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
                        }

                        if (name1.endsWith("Block")) {
                            if (expansionInfo1.getBlockName().equals(expansionInfo2.getBlockName())) {
                                return -1;
                            }
                        }

                        if (name2.endsWith("Block")) {
                            if (expansionInfo1.getBlockName().equals(expansionInfo2.getBlockName())) {
                                return 1;
                            }
                        }

                        return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
                    case SUPPLEMENTAL:
                        if (expansionInfo1.getBlockName() == null) {
                            if (expansionInfo2.getBlockName() == null) {
                                return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
                            }

                            return -1;
                        }

                        if (expansionInfo2.getBlockName() == null) {
                            return 1;
                        }

                        if (expansionInfo1.getBlockName().equals(expansionInfo2.getBlockName())) {
                            //If release date is the same, sort alphabetically.
                            if (expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate()) == 0) {
                                return name1.compareTo(name2);
                            }
                            return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
                        }

                        if (expansionInfo1.getBlockName().startsWith("Duel Decks")) {
                            if (expansionInfo1.getBlockName().startsWith("Duel Decks: Anthology")) {
                                return 1;
                            }
                            return 1;
                        }
                        if (expansionInfo2.getBlockName().startsWith("Duel Decks")) {
                            return -1;
                        }

                        ExpansionInfo blockInfo1 = expansionInfo.get(expansionInfo1.getBlockName());
                        ExpansionInfo blockInfo2 = expansionInfo.get(expansionInfo2.getBlockName());

                        return blockInfo2.getReleaseDate().compareTo(blockInfo1.getReleaseDate());
                    default:
                        return expansionInfo2.getReleaseDate().compareTo(expansionInfo1.getReleaseDate());
                }
            }
            return expansionInfo1.getType().compareTo(expansionInfo2.getType());
        });
        if (!formats.isEmpty()) {
            formats.add(0, CUSTOM);
            formats.add(0, VINTAGE_LEGACY);
            formats.add(0, MODERN);
            formats.add(0, EXTENDED);
            formats.add(0, FRONTIER);
            formats.add(0, STANDARD);

        }
        formats.add(0, ALL);
    }

    private static String getBlockDisplayName(String blockName) {
        return "* " + blockName + " Block";
    }
    // Attention -Month is 0 Based so Feb = 1 for example.
    private static final Date extendedDate = new GregorianCalendar(2009, 7, 20).getTime();
    private static final Date frontierDate = new GregorianCalendar(2014, 6, 17).getTime();
    private static final Date modernDate = new GregorianCalendar(2003, 6, 20).getTime();

    // for all sets just return empty list
    private static final List<String> all = new ArrayList<>();

    static {
        buildLists();
    }
}
