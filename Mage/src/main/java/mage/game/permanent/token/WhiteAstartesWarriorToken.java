package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class WhiteAstartesWarriorToken extends TokenImpl {

    public WhiteAstartesWarriorToken() {
        super("Astartes Warrior Token", "2/2 white Astartes Warrior creature tokens with vigilance");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ASTARTES);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(VigilanceAbility.getInstance());

        availableImageSetCodes.addAll(Arrays.asList("40K"));
        this.setTokenType(2);
    }

    public WhiteAstartesWarriorToken(final WhiteAstartesWarriorToken token) {
        super(token);
    }

    @Override
    public WhiteAstartesWarriorToken copy() {
        return new WhiteAstartesWarriorToken(this);
    }
}
