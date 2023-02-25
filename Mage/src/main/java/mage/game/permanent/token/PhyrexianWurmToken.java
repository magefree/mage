package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ToxicAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

public final class PhyrexianWurmToken extends TokenImpl {

    public PhyrexianWurmToken() {
        this(0);
    }

    public PhyrexianWurmToken(int amount) {
        super("Phyrexian Wurm Token", "X/X green Phyrexian Wurm creature token with trample and toxic 1");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.WURM);
        power = new MageInt(amount);
        toughness = new MageInt(amount);
        addAbility(TrampleAbility.getInstance());
        addAbility(new ToxicAbility(1));

        availableImageSetCodes = Arrays.asList("ONC");
    }

    public PhyrexianWurmToken(final PhyrexianWurmToken token) {
        super(token);
    }

    public PhyrexianWurmToken copy() {
        return new PhyrexianWurmToken(this);
    }
}