

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;

/**
 *
 * @author spjspj
 */
public final class RakdosGuildmageGoblinToken extends TokenImpl {

    public RakdosGuildmageGoblinToken() {
        super("Goblin Token", "2/1 red Goblin creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.GOBLIN);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
    }

    public RakdosGuildmageGoblinToken(final RakdosGuildmageGoblinToken token) {
        super(token);
    }

    public RakdosGuildmageGoblinToken copy() {
        return new RakdosGuildmageGoblinToken(this);
    }
}
