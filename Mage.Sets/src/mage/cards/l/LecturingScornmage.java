package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.ReparteeAbility;
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
public final class LecturingScornmage extends CardImpl {

    public LecturingScornmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, put a +1/+1 counter on this creature.
        this.addAbility(new ReparteeAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private LecturingScornmage(final LecturingScornmage card) {
        super(card);
    }

    @Override
    public LecturingScornmage copy() {
        return new LecturingScornmage(this);
    }
}
