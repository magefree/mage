package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public class ZombieMenaceToken extends TokenImpl {

    public ZombieMenaceToken(int xValue) {
        super("Zombie", "X/X blue and black Zombie creature token with menace");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        color.setBlack(true);
        subtype.add(SubType.ZOMBIE);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
        addAbility(new MenaceAbility());
    }

    private ZombieMenaceToken(final ZombieMenaceToken token) {
        super(token);
    }

    @Override
    public ZombieMenaceToken copy() {
        return new ZombieMenaceToken(this);
    }
}
