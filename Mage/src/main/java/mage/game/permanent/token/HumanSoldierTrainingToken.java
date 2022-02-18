package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.TrainingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public class HumanSoldierTrainingToken extends TokenImpl {

    public HumanSoldierTrainingToken() {
        super("Human Soldier", "1/1 green and white Human Soldier creature token with training");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // Training (Whenever this creature attacks with another creature with greater power, put a +1/+1 counter on this creature.)
        this.addAbility(new TrainingAbility());

        availableImageSetCodes = Arrays.asList("VOW");
    }

    private HumanSoldierTrainingToken(final HumanSoldierTrainingToken token) {
        super(token);
    }

    @Override
    public HumanSoldierTrainingToken copy() {
        return new HumanSoldierTrainingToken(this);
    }
}
