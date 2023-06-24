package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * spjspj
 */
public final class QuestForTheGravelordZombieToken extends TokenImpl {

    public QuestForTheGravelordZombieToken() {
        super("Zombie Giant Token", "5/5 black Zombie Giant creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.GIANT);

        color.setBlack(true);
        power = new MageInt(5);
        toughness = new MageInt(5);
    }

    public QuestForTheGravelordZombieToken(final QuestForTheGravelordZombieToken token) {
        super(token);
    }

    public QuestForTheGravelordZombieToken copy() {
        return new QuestForTheGravelordZombieToken(this);
    }
}
