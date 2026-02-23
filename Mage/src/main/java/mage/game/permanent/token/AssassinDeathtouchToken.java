package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DestroyPlaneswalkerWhenDamagedTriggeredAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class AssassinDeathtouchToken extends TokenImpl {

    public AssassinDeathtouchToken() {
        super("Assassin Token", "1/1 black Assassin creature token with deathtouch and \"Whenever this creature deals damage to a planeswalker, destroy that planeswalker.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ASSASSIN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(DeathtouchAbility.getInstance());
        addAbility(new DestroyPlaneswalkerWhenDamagedTriggeredAbility());
    }

    private AssassinDeathtouchToken(final AssassinDeathtouchToken token) {
        super(token);
    }

    public AssassinDeathtouchToken copy() {
        return new AssassinDeathtouchToken(this);
    }
}
