package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class TrainingDroid extends CardImpl {
    public TrainingDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.addSubType(SubType.DROID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Repair 2
        this.addAbility(new RepairAbility(2));
    }

    public TrainingDroid(final TrainingDroid card) {
        super(card);
    }

    @Override
    public TrainingDroid copy() {
        return new TrainingDroid(this);
    }
}
