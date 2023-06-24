package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author North
 */
public final class GoblinToken extends TokenImpl {

    public GoblinToken(boolean withHaste) {
        this();

        // token image don't have haste info so it's ok to use same class for different versions
        if (withHaste) {
            addAbility(HasteAbility.getInstance());
            this.description = "1/1 red Goblin creature token with haste";
        }
    }

    public GoblinToken() {
        super("Goblin Token", "1/1 red Goblin creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GOBLIN);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    public GoblinToken(final GoblinToken token) {
        super(token);
    }

    public GoblinToken copy() {
        return new GoblinToken(this);
    }
}
