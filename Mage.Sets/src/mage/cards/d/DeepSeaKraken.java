package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeepSeaKraken extends CardImpl {

    public DeepSeaKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{U}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Deep-Sea Kraken can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Suspend 9-{2}{U}
        this.addAbility(new SuspendAbility(9, new ManaCostsImpl<>("{2}{U}"), this));

        // Whenever an opponent casts a spell, if Deep-Sea Kraken is suspended, remove a time counter from it.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.EXILED,
                new RemoveCounterSourceEffect(CounterType.TIME.createInstance())
                        .setText("remove a time counter from it"),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.NONE)
                .withInterveningIf(SuspendedCondition.instance));
    }

    private DeepSeaKraken(final DeepSeaKraken card) {
        super(card);
    }

    @Override
    public DeepSeaKraken copy() {
        return new DeepSeaKraken(this);
    }
}
