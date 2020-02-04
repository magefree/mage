package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author North
 */
public final class GoblinToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("10E", "ALA", "SOM", "M10", "NPH", "M13", "RTR",
                "MMA", "M15", "C14", "KTK", "EVG", "DTK", "ORI", "DDG", "DDN", "DD3EVG", "MM2",
                "MM3", "EMA", "C16", "DOM", "ANA", "RNA", "WAR", "MH1"));
    }

    public GoblinToken(boolean withHaste) {
        this();
        if (withHaste) {
            addAbility(HasteAbility.getInstance());
            this.description = "1/1 red Goblin creature token with haste";
        }
    }

    public GoblinToken() {
        this(null, 0);
    }

    public GoblinToken(String setCode) {
        this(setCode, 0);
    }

    public GoblinToken(String setCode, int tokenType) {
        super("Goblin", "1/1 red Goblin creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
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
