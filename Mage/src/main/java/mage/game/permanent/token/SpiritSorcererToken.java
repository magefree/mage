package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/*
* @author muz
*/
public final class SpiritSorcererToken extends TokenImpl {

    public SpiritSorcererToken() {
        super("Spirit Sorcerer Token", "3/3 blue Spirit Sorcerer enchantment creature token with flying");
        cardType.add(CardType.ENCHANTMENT);
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SPIRIT);
        subtype.add(SubType.SORCERER);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(FlyingAbility.getInstance());
    }

    private SpiritSorcererToken(final SpiritSorcererToken token) {
        super(token);
    }

    public SpiritSorcererToken copy() {
        return new SpiritSorcererToken(this);
    }
}
