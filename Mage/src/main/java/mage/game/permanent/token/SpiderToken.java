/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public class SpiderToken extends Token {

    public SpiderToken() {
        super("Spider", "1/2 green Spider creature token with reach");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Spider");
        power = new MageInt(1);
        toughness = new MageInt(2);
        addAbility(ReachAbility.getInstance());
    }
}
