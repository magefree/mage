package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LongRangeSensor extends CardImpl {

    public LongRangeSensor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");

        // Whenever you attack a player, put a charge counter on this artifact.
        this.addAbility(new AttacksPlayerWithCreaturesTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), SetTargetPointer.NONE
        ));

        // {1}, Remove two charge counters from this artifact: Discover 4. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new DiscoverEffect(4), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance(2)));
        this.addAbility(ability);
    }

    private LongRangeSensor(final LongRangeSensor card) {
        super(card);
    }

    @Override
    public LongRangeSensor copy() {
        return new LongRangeSensor(this);
    }
}
