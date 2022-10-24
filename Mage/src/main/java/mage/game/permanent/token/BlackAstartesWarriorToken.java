package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;
import mage.abilities.keyword.MenaceAbility;

/**
 * @author TheElk801
 */
public final class BlackAstartesWarriorToken extends TokenImpl {

    public BlackAstartesWarriorToken() {
        super("Astartes Warrior Token", "2/2 black Astartes Warrior creature tokens with menace");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.ASTARTES);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(new MenaceAbility());

        availableImageSetCodes.addAll(Arrays.asList("40K"));
    }

    public BlackAstartesWarriorToken(final BlackAstartesWarriorToken token) {
        super(token);
    }

    @Override
    public BlackAstartesWarriorToken copy() {
        return new BlackAstartesWarriorToken(this);
    }
}
