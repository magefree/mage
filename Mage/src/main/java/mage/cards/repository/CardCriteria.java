package mage.cards.repository;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author North
 */
public class CardCriteria {

    private String name;
    private String nameExact;
    private String rules;
    private final List<String> setCodes;
    private final List<String> ignoreSetCodes; // sets to ignore, use with little amount of sets (example: ignore sets with snow lands)
    private final List<CardType> types;
    private final List<CardType> notTypes;
    private final List<SuperType> supertypes;
    private final List<SuperType> notSupertypes;
    private final List<SubType> subtypes;
    private final List<Rarity> rarities;
    private Boolean doubleFaced;
    private boolean black;
    private boolean blue;
    private boolean green;
    private boolean red;
    private boolean white;
    private boolean colorless;
    private Integer manaValue;
    private String sortBy;
    private Long start;
    private Long count;
    // compare numerical card numbers (123b -> 123)
    private int minCardNumber;
    private int maxCardNumber;

    public CardCriteria() {
        this.setCodes = new ArrayList<>();
        this.ignoreSetCodes = new ArrayList<>();
        this.rarities = new ArrayList<>();
        this.types = new ArrayList<>();
        this.notTypes = new ArrayList<>();
        this.supertypes = new ArrayList<>();
        this.notSupertypes = new ArrayList<>();
        this.subtypes = new ArrayList<>();

        this.black = true;
        this.blue = true;
        this.green = true;
        this.red = true;
        this.white = true;
        this.colorless = true;

        this.minCardNumber = Integer.MIN_VALUE;
        this.maxCardNumber = Integer.MAX_VALUE;
    }

    public CardCriteria black(boolean black) {
        this.black = black;
        return this;
    }

    public CardCriteria blue(boolean blue) {
        this.blue = blue;
        return this;
    }

    public CardCriteria green(boolean green) {
        this.green = green;
        return this;
    }

    public CardCriteria red(boolean red) {
        this.red = red;
        return this;
    }

    public CardCriteria white(boolean white) {
        this.white = white;
        return this;
    }

    public CardCriteria colorless(boolean colorless) {
        this.colorless = colorless;
        return this;
    }

    public CardCriteria doubleFaced(boolean doubleFaced) {
        this.doubleFaced = doubleFaced;
        return this;
    }

    public CardCriteria name(String name) {
        this.name = name;
        return this;
    }

    public CardCriteria nameExact(String nameExact) {
        this.nameExact = nameExact;
        return this;
    }

    public CardCriteria rules(String rules) {
        this.rules = rules;
        return this;
    }

    public CardCriteria start(Long start) {
        this.start = start;
        return this;
    }

    public CardCriteria count(Long count) {
        this.count = count;
        return this;
    }

    public CardCriteria rarities(Rarity... rarities) {
        this.rarities.addAll(Arrays.asList(rarities));
        return this;
    }

    public CardCriteria setCodes(String... setCodes) {
        this.setCodes.addAll(Arrays.asList(setCodes));
        return this;
    }

    public CardCriteria ignoreSetCodes(String... ignoreSetCodes) {
        this.ignoreSetCodes.addAll(Arrays.asList(ignoreSetCodes));
        return this;
    }

    public CardCriteria ignoreSetsWithSnowLands() {
        this.ignoreSetCodes.addAll(CardRepository.snowLandSetCodes);
        return this;
    }

    public CardCriteria types(CardType... types) {
        this.types.addAll(Arrays.asList(types));
        return this;
    }

    public CardCriteria notTypes(CardType... types) {
        this.notTypes.addAll(Arrays.asList(types));
        return this;
    }

    public CardCriteria supertypes(SuperType... supertypes) {
        this.supertypes.addAll(Arrays.asList(supertypes));
        return this;
    }

    public CardCriteria notSupertypes(SuperType... supertypes) {
        this.notSupertypes.addAll(Arrays.asList(supertypes));
        return this;
    }

    public CardCriteria subtypes(SubType... subtypes) {
        this.subtypes.addAll(Arrays.asList(subtypes));
        return this;
    }

    public CardCriteria manaValue(Integer manaValue) {
        this.manaValue = manaValue;
        return this;
    }

    public CardCriteria minCardNumber(int minCardNumber) {
        this.minCardNumber = minCardNumber;
        return this;
    }

    public CardCriteria maxCardNumber(int maxCardNumber) {
        this.maxCardNumber = maxCardNumber;
        return this;
    }

    public CardCriteria setOrderBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public void buildQuery(QueryBuilder qb) throws SQLException {
        optimize();

        Where where = qb.where();
        where.eq("nightCard", false);
        where.eq("splitCardHalf", false);
        int clausesCount = 2;
        if (name != null) {
            where.like("name", new SelectArg('%' + name + '%'));
            clausesCount++;
        }
        if (nameExact != null) {
            where.like("name", new SelectArg(nameExact));
            clausesCount++;
        }
        if (rules != null) {
            where.like("rules", new SelectArg('%' + rules + '%'));
            clausesCount++;
        }

        if (doubleFaced != null) {
            where.eq("doubleFaced", doubleFaced);
            clausesCount++;
        }

        for (Rarity rarity : rarities) {
            where.eq("rarity", rarity);
        }
        if (!rarities.isEmpty()) {
            where.or(rarities.size());
            clausesCount++;
        }

        for (String setCode : setCodes) {
            where.eq("setCode", setCode);
        }
        if (!setCodes.isEmpty()) {
            where.or(setCodes.size());
            clausesCount++;
        }

        for (String ignoreSetCode : ignoreSetCodes) {
            where.ne("setCode", ignoreSetCode);
        }
        if (!ignoreSetCodes.isEmpty()) {
            where.or(ignoreSetCodes.size());
            clausesCount++;
        }

        if (types.size() != 7) { //if all types selected - no selection needed (Tribal and Conspiracy not selectable yet)
            for (CardType type : types) {
                where.like("types", new SelectArg('%' + type.name() + '%'));
            }
            if (!types.isEmpty()) {
                where.or(types.size());
                clausesCount++;
            }
        }

        for (CardType type : notTypes) {
            where.not().like("types", new SelectArg('%' + type.name() + '%'));
            clausesCount++;
        }

        for (SuperType superType : supertypes) {
            where.like("supertypes", new SelectArg('%' + superType.name() + '%'));
            clausesCount++;
        }
        for (SuperType superType : notSupertypes) {
            where.not().like("supertypes", new SelectArg('%' + superType.name() + '%'));
            clausesCount++;
        }

        for (SubType subType : subtypes) {
            where.like("subtypes", new SelectArg('%' + subType.toString() + '%'));
            clausesCount++;
        }

        if (manaValue != null) {
            where.eq("manaValue", manaValue);
            clausesCount++;
        }

        int colorClauses = 0;
        if (black) {
            where.eq("black", true);
            colorClauses++;
        }
        if (blue) {
            where.eq("blue", true);
            colorClauses++;
        }
        if (green) {
            where.eq("green", true);
            colorClauses++;
        }
        if (red) {
            where.eq("red", true);
            colorClauses++;
        }
        if (white) {
            where.eq("white", true);
            colorClauses++;
        }
        if (colorless) {
            where.eq("black", false).eq("blue", false).eq("green", false).eq("red", false).eq("white", false);
            where.and(5);
            colorClauses++;
        }
        if (colorClauses > 0) {
            where.or(colorClauses);
            clausesCount++;
        }

        if (minCardNumber != Integer.MIN_VALUE) {
            where.ge("cardNumberAsInt", minCardNumber);
            clausesCount++;
        }

        if (maxCardNumber != Integer.MAX_VALUE) {
            where.le("cardNumberAsInt", maxCardNumber);
            clausesCount++;
        }

        if (clausesCount > 0) {
            where.and(clausesCount);
        } else {
            where.eq("cardNumber", new SelectArg(0));
        }

        if (start != null) {
            qb.offset(start);
        }
        if (count != null) {
            qb.limit(count);
        }

        if (sortBy != null) {
            qb.orderBy(sortBy, true);
        }
    }

    private CardCriteria optimize() {
        // remove rarity
        if (rarities.size() > 0) {
            List<Rarity> unusedRarities = new ArrayList<>(Arrays.asList(Rarity.values()));
            unusedRarities.removeAll(rarities);
            if (unusedRarities.isEmpty()) {
                rarities.clear();
            }
        }

        // remove color
        if (black && blue && green && red && white && colorless) {
            black = false;
            blue = false;
            green = false;
            red = false;
            white = false;
            colorless = false;
        }

        // remove card type
        if (types.size() > 0) {
            List<CardType> unusedCardTypes = new ArrayList<>(Arrays.asList(CardType.values()));
            unusedCardTypes.removeAll(types);
            if (unusedCardTypes.stream().noneMatch(CardType::isIncludeInSearch)) {
                types.clear();
            }
        }

        return this;
    }

    public String getName() {
        return name;
    }

    public String getNameExact() {
        return nameExact;
    }

    public String getRules() {
        return rules;
    }

    public List<String> getSetCodes() {
        return setCodes;
    }

    public List<String> getIgnoreSetCodes() {
        return ignoreSetCodes;
    }

    public List<CardType> getTypes() {
        return types;
    }

    public List<CardType> getNotTypes() {
        return notTypes;
    }

    public List<SuperType> getSupertypes() {
        return supertypes;
    }

    public List<SuperType> getNotSupertypes() {
        return notSupertypes;
    }

    public List<SubType> getSubtypes() {
        return subtypes;
    }

    public List<Rarity> getRarities() {
        return rarities;
    }

    public Boolean getDoubleFaced() {
        return doubleFaced;
    }

    public boolean isBlack() {
        return black;
    }

    public boolean isBlue() {
        return blue;
    }

    public boolean isGreen() {
        return green;
    }

    public boolean isRed() {
        return red;
    }

    public boolean isWhite() {
        return white;
    }

    public boolean isColorless() {
        return colorless;
    }

    public Integer getManaValue() {
        return manaValue;
    }

    public String getSortBy() {
        return sortBy;
    }

    public Long getStart() {
        return start;
    }

    public Long getCount() {
        return count;
    }

    public int getMinCardNumber() {
        return minCardNumber;
    }

    public int getMaxCardNumber() {
        return maxCardNumber;
    }

}
