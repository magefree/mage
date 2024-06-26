package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class AssassinMenaceToken extends TokenImpl {

    public AssassinMenaceToken() {
        super("Assassin Token", "1/1 black Assassin creature token menace");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ASSASSIN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new MenaceAbility(false));
    }

    private AssassinMenaceToken(final AssassinMenaceToken token) {
        super(token);
    }

    public AssassinMenaceToken copy() {
        return new AssassinMenaceToken(this);
    }
}
