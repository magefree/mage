package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public class SpiritWarriorToken extends TokenImpl {

    public SpiritWarriorToken() {
        this(1);
    }

    public SpiritWarriorToken(int x) {
        super("Spirit Warrior Token", "X/X black and green Spirit Warrior creature token, where X is the greatest toughness among creatures you control");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.power = new MageInt(x);
        this.toughness = new MageInt(x);
    }

    private SpiritWarriorToken(final SpiritWarriorToken token) {
        super(token);
    }

    public SpiritWarriorToken copy() {
        return new SpiritWarriorToken(this);
    }
}