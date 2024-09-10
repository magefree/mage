package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class PlantWarriorToken extends TokenImpl {

    public PlantWarriorToken() {
        super("Plant Warrior Token", "4/2 green Plant Warrior creature token with reach");
        color.setGreen(true);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PLANT);
        subtype.add(SubType.WARRIOR);
        power = new MageInt(4);
        toughness = new MageInt(2);

        this.addAbility(ReachAbility.getInstance());
    }

    private PlantWarriorToken(final PlantWarriorToken token) {
        super(token);
    }

    public PlantWarriorToken copy() {
        return new PlantWarriorToken(this);
    }
}
