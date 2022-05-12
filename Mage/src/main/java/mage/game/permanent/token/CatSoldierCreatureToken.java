
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class CatSoldierCreatureToken extends TokenImpl {

    public CatSoldierCreatureToken() {
        super("Cat Soldier Token", "1/1 white Cat Soldier creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        
        subtype.add(SubType.CAT);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        
        this.addAbility(VigilanceAbility.getInstance());
        this.setOriginalExpansionSetCode("BNG");
    }

    public CatSoldierCreatureToken(final CatSoldierCreatureToken token) {
        super(token);
    }

    public CatSoldierCreatureToken copy() {
        return new CatSoldierCreatureToken(this);
    }
}