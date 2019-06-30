/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fireshoes
 */
public final class SpiderToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("ISD", "EMN", "C15", "SHM", "MH1"));
    }

    public SpiderToken() {
        this(null, 0);
    }

    public SpiderToken(String setCode) {
        this(setCode, 0);
    }

    public SpiderToken(String setCode, int tokenType) {
        super("Spider", "1/2 green Spider creature token with reach");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.SPIDER);
        power = new MageInt(1);
        toughness = new MageInt(2);
        addAbility(ReachAbility.getInstance());
    }

    public SpiderToken(final SpiderToken token) {
        super(token);
    }

    public SpiderToken copy() {
        return new SpiderToken(this);
    }
}
