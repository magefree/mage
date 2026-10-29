package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author DarioBarrabinoDev
 */
public final class LaserbeakToken extends TokenImpl {

    public LaserbeakToken() {
        super("Laserbeak", "Laserbeak, a legendary 2/2 blue Robot artifact creature token with flying and hexproof");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.ROBOT);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HexproofAbility.getInstance());
    }

    private LaserbeakToken(final LaserbeakToken token) {
        super(token);
    }

    public LaserbeakToken copy() {
        return new LaserbeakToken(this);
    }
}
