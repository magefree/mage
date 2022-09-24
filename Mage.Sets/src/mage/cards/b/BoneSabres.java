package mage.cards.b;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneSabres extends CardImpl {

    public BoneSabres(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, put four +1/+1 counters on it.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(4))
                        .setText("put four +1/+1 counters on it"),
                AttachmentType.EQUIPMENT, false, SetTargetPointer.PERMANENT
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private BoneSabres(final BoneSabres card) {
        super(card);
    }

    @Override
    public BoneSabres copy() {
        return new BoneSabres(this);
    }
}
