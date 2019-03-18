

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.ReachAbility;

/**
 *
 * @author spjspj
 */
public final class RenownedWeaverSpiderToken extends TokenImpl {

    public RenownedWeaverSpiderToken() {
        super("Spider", "1/3 green Spider enchantment creature token with reach");
        this.setOriginalExpansionSetCode("JOU");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setColor(ObjectColor.GREEN);
        subtype.add(SubType.SPIDER);
        power = new MageInt(1);
        toughness = new MageInt(3);
        this.addAbility(ReachAbility.getInstance());
    }

    public RenownedWeaverSpiderToken(final RenownedWeaverSpiderToken token) {
        super(token);
    }

    public RenownedWeaverSpiderToken copy() {
        return new RenownedWeaverSpiderToken(this);
    }
}
