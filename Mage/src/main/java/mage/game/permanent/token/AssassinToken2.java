package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DestroyPlaneswalkerWhenDamagedTriggeredAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class AssassinToken2 extends TokenImpl {

    public AssassinToken2() {
        super("Assassin Token", "1/1 black Assassin creature token with deathtouch and \"Whenever this creature deals damage to a planeswalker, destroy that planeswalker.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ASSASSIN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(DeathtouchAbility.getInstance());
        addAbility(new DestroyPlaneswalkerWhenDamagedTriggeredAbility());

        setOriginalExpansionSetCode("WAR");
    }

    private AssassinToken2(final AssassinToken2 token) {
        super(token);
    }

    public AssassinToken2 copy() {
        return new AssassinToken2(this);
    }
}

