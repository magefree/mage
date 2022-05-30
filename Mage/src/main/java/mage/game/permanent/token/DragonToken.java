package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class DragonToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("DTK", "MMA", "ALA", "MM3", "C17", "WAR", "MED"));
    }

    public DragonToken() {
        this(null, 0);
    }

    public DragonToken(String setCode) {
        this(setCode, 0);
    }

    public DragonToken(String setCode, int tokenType) {
        super("Dragon Token", "4/4 red Dragon creature token with flying");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public DragonToken(final DragonToken token) {
        super(token);
    }

    public DragonToken copy() {
        return new DragonToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("C17")) {
            setTokenType(1);
        }
    }
}
