package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class BatToken extends TokenImpl {
    
    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("MMA", "C17"));
    }

    public BatToken() {
        super("Bat", "1/1 black Bat creature token with flying");
        availableImageSetCodes = tokenImageSets;
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.BAT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());

        this.setOriginalExpansionSetCode("MMA");
    }
    public BatToken(final BatToken token) {
        super(token);
    }

    public BatToken copy() {
        return new BatToken(this);
    }
}
