
package mage.cards.repository;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.Rarity;

/**
 *
 * @author North
 */
public class CardCriteria {

    private String name;
    private String nameExact;
    private String rules;
    private final List<String> setCodes;
    private final List<CardType> types;
    private final List<CardType> notTypes;
    private final List<String> supertypes;
    private final List<String> notSupertypes;
    private final List<String> subtypes;
    private final List<Rarity> rarities;
    private Boolean doubleFaced;
    private boolean black;
    private boolean blue;
    private boolean green;
    private boolean red;
    private boolean white;
    private boolean colorless;
    private Integer convertedManaCost;
    private String sortBy;
    private Long start;
    private Long count;
    private int minCardNumber;
    private int maxCardNumber;

    public CardCriteria() {
        this.setCodes = new ArrayList<>();
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

    public CardCriteria types(CardType... types) {
        this.types.addAll(Arrays.asList(types));
        return this;
    }

    public CardCriteria notTypes(CardType... types) {
        this.notTypes.addAll(Arrays.asList(types));
        return this;
    }

    public CardCriteria supertypes(String... supertypes) {
        this.supertypes.addAll(Arrays.asList(supertypes));
        return this;
    }

    public CardCriteria notSupertypes(String... supertypes) {
        this.notSupertypes.addAll(Arrays.asList(supertypes));
        return this;
    }

    public CardCriteria subtypes(String... subtypes) {
        this.subtypes.addAll(Arrays.asList(subtypes));
        return this;
    }

    public CardCriteria convertedManaCost(Integer convertedManaCost) {
        this.convertedManaCost = convertedManaCost;
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

        for (String superType : supertypes) {
            where.like("supertypes", new SelectArg('%' + superType + '%'));
            clausesCount++;
        }
        for (String subType : notSupertypes) {
            where.not().like("supertypes", new SelectArg('%' + subType + '%'));
            clausesCount++;
        }

        for (String subType : subtypes) {
            where.like("subtypes", new SelectArg('%' + subType + '%'));
            clausesCount++;
        }

        if (convertedManaCost != null) {
            where.eq("convertedManaCost", convertedManaCost);
            clausesCount++;
        }

        if (!black || !blue || !green || !red || !white || !colorless) {
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
        }

        if (minCardNumber != Integer.MIN_VALUE) {
            where.ge("cardNumber", minCardNumber);
            clausesCount++;
        }

        if (maxCardNumber != Integer.MAX_VALUE) {
            where.le("cardNumber", maxCardNumber);
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
}
