package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

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

        availableImageSetCodes = Arrays.asList("10E", "ALA", "SOM", "M10", "NPH", "M13", "RTR",
                "MMA", "M15", "C14", "KTK", "EVG", "DTK", "ORI", "DDG", "DDN", "EVG", "MM2",
                "MM3", "EMA", "C16", "DOM", "ANA", "RNA", "WAR", "MH1", "TSR", "MH2", "AFR");
    }

    public GoblinToken(final GoblinToken token) {
        super(token);
    }

    public GoblinToken copy() {
        return new GoblinToken(this);
    }
}
