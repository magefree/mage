package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author weirddan455
 */
public class DragonIllusionToken extends TokenImpl {

    public DragonIllusionToken(int xValue) {
        super("Dragon Illusion", "X/X red Dragon Illusion creature token with flying and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        addAbility(FlyingAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    private DragonIllusionToken(final DragonIllusionToken token) {
        super(token);
    }

    @Override
    public DragonIllusionToken copy() {
        return new DragonIllusionToken(this);
    }
}
