package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

public class ZombieMenaceToken extends TokenImpl {

    public ZombieMenaceToken() {
        this(0);
    }

    public ZombieMenaceToken(int xValue) {
        super("Zombie Token", "X/X blue and black Zombie creature token with menace");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

        this.addAbility(new MenaceAbility());

        availableImageSetCodes.addAll(Arrays.asList("MID"));
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("MID")) {
            this.setTokenType(2);
        }
    }

    private ZombieMenaceToken(final ZombieMenaceToken token) {
        super(token);
    }

    @Override
    public ZombieMenaceToken copy() {
        return new ZombieMenaceToken(this);
    }
}
