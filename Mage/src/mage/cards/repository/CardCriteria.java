/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.repository;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.Constants.CardType;
import mage.Constants.Rarity;

/**
 *
 * @author North
 */
public class CardCriteria {

    private String name;
    private String rules;
    private List<String> setCodes;
    private List<CardType> types;
    private List<CardType> notTypes;
    private List<String> supertypes;
    private List<String> notSupertypes;
    private List<String> subtypes;
    private List<Rarity> rarities;
    private Boolean doubleFaced;
    private boolean black;
    private boolean blue;
    private boolean green;
    private boolean red;
    private boolean white;
    private boolean colorless;
    private Long start;
    private Long count;

    public CardCriteria() {
        this.setCodes = new ArrayList<String>();
        this.rarities = new ArrayList<Rarity>();
        this.types = new ArrayList<CardType>();
        this.notTypes = new ArrayList<CardType>();
        this.supertypes = new ArrayList<String>();
        this.notSupertypes = new ArrayList<String>();
        this.subtypes = new ArrayList<String>();

        this.black = true;
        this.blue = true;
        this.green = true;
        this.red = true;
        this.white = true;
        this.colorless = true;
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

    public void buildQuery(QueryBuilder qb) throws SQLException {
        Where where = qb.where();
        int clausesCount = 0;
        if (name != null) {
            where.like("name", new SelectArg('%' + name + '%'));
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

        for (CardType type : types) {
            where.like("types", new SelectArg('%' + type.name() + '%'));
        }
        if (!types.isEmpty()) {
            where.or(types.size());
            clausesCount++;
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
            where.or(colorClauses);
            clausesCount++;
        }

        where.and(clausesCount);

        if (start != null) {
            qb.offset(start);
        }
        if (count != null) {
            qb.limit(count);
        }

        qb.orderBy("cardNumber", true);
    }
}
