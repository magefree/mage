package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class WasitoraCatDragonToken extends TokenImpl {

    public WasitoraCatDragonToken() {
        super("Cat Dragon Token", "3/3 black, red, and green Cat Dragon creature token with flying");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DRAGON);
        color.setBlack(true);
        color.setRed(true);
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
    }

    public WasitoraCatDragonToken(final WasitoraCatDragonToken token) {
        super(token);
    }

    public WasitoraCatDragonToken copy() {
        return new WasitoraCatDragonToken(this);
    }
}
