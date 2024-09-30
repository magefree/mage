package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OptimisticScavenger extends CardImpl {

    public OptimisticScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, put a +1/+1 counter on target creature.
        Ability ability = new EerieAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private OptimisticScavenger(final OptimisticScavenger card) {
        super(card);
    }

    @Override
    public OptimisticScavenger copy() {
        return new OptimisticScavenger(this);
    }
}
