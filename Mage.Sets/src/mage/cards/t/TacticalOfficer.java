package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TacticalOfficer extends CardImpl {

    public TacticalOfficer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you attack while creatures you control have total power 8 or greater, put a +1/+1 counter on this creature. Untap it.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()), 1
        ).withTriggerCondition(FormidableCondition.instance);
        ability.addEffect(new UntapSourceEffect().setText("Untap it"));
        this.addAbility(ability);
    }

    private TacticalOfficer(final TacticalOfficer card) {
        super(card);
    }

    @Override
    public TacticalOfficer copy() {
        return new TacticalOfficer(this);
    }
}
