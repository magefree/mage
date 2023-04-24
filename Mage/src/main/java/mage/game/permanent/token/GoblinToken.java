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

        availableImageSetCodes = Arrays.asList("10E", "ALA", "SOM", "M10", "M13", "RTR",
                "MMA", "M15", "C14", "KTK", "EVG", "DTK", "ORI", "DDG", "DDN",
                "MM3", "EMA", "DOM", "RNA", "WAR", "MH1", "TSR", "MH2", "AFR",
                "NEC", "M19", "CM2", "PCA", "DD1", "DDS", "DDT", "A25", "GRN",
                "GK1", "DMU", "DMR", "ONC", "MOC");
    }

    public GoblinToken(final GoblinToken token) {
        super(token);
    }

    public GoblinToken copy() {
        return new GoblinToken(this);
    }
}
