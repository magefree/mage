package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DestroyPlaneswalkerWhenDamagedTriggeredAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class AssassinToken3 extends TokenImpl {

    public AssassinToken3() {
        super("Assassin Token", "1/1 black Assassin creature token with menace");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ASSASSIN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new MenaceAbility());
    }

    private AssassinToken3(final AssassinToken3 token) {
        super(token);
    }

    public AssassinToken3 copy() {
        return new AssassinToken3(this);
    }
}

