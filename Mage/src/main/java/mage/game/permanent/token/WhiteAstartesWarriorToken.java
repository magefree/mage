package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WhiteAstartesWarriorToken extends TokenImpl {

    public WhiteAstartesWarriorToken() {
        super("Astartes Warrior Token", "2/2 white Astartes Warrior creature token with vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ASTARTES);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(VigilanceAbility.getInstance());
    }

    public WhiteAstartesWarriorToken(final WhiteAstartesWarriorToken token) {
        super(token);
    }

    @Override
    public WhiteAstartesWarriorToken copy() {
        return new WhiteAstartesWarriorToken(this);
    }
}
