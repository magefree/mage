

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ReachAbility;

import java.util.Arrays;

/**
 *
 * @author spjspj
 */
public final class RenownedWeaverSpiderToken extends TokenImpl {

    public RenownedWeaverSpiderToken() {
        super("Spider Token", "1/3 green Spider enchantment creature token with reach");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setColor(ObjectColor.GREEN);
        subtype.add(SubType.SPIDER);
        power = new MageInt(1);
        toughness = new MageInt(3);

        this.addAbility(ReachAbility.getInstance());

        availableImageSetCodes = Arrays.asList("JOU");
    }

    public RenownedWeaverSpiderToken(final RenownedWeaverSpiderToken token) {
        super(token);
    }

    public RenownedWeaverSpiderToken copy() {
        return new RenownedWeaverSpiderToken(this);
    }
}
