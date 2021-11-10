

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author spjspj
 */
public final class FlurryOfHornsMinotaurToken extends TokenImpl {

    public FlurryOfHornsMinotaurToken() {
        super("Minotaur Token", "2/3 red Minotaur creature tokens with haste");
        this.setOriginalExpansionSetCode("JOU");
        cardType.add(CardType.CREATURE);
        color.setColor(ObjectColor.RED);
        subtype.add(SubType.MINOTAUR);
        power = new MageInt(2);
        toughness = new MageInt(3);
        addAbility(HasteAbility.getInstance());
    }

    public FlurryOfHornsMinotaurToken(final FlurryOfHornsMinotaurToken token) {
        super(token);
    }

    public FlurryOfHornsMinotaurToken copy() {
        return new FlurryOfHornsMinotaurToken(this);
    }
}
