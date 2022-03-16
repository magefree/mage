package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author JayDi85
 */
public final class WardenSphinxToken extends TokenImpl {

    public WardenSphinxToken() {
        super("Sphinx Token", "4/4 white and blue Sphinx creature token with flying and vigilance");
        this.setOriginalExpansionSetCode("RNA");
        color.setWhite(true);
        color.setBlue(true);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPHINX);
        power = new MageInt(4);
        toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
    }

    public WardenSphinxToken(final WardenSphinxToken token) {
        super(token);
    }

    public WardenSphinxToken copy() {
        return new WardenSphinxToken(this);
    }
}
