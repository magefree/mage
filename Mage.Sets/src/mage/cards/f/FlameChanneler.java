package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellControlledDealsDamageTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlameChanneler extends TransformingDoubleFacedCard {

    public FlameChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{1}{R}",
                "Embodiment of Flame",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.WIZARD}, "R"
        );

        // Flame Channeler
        this.getLeftHalfCard().setPT(2, 2);

        // When a spell you control deals damage, transform Flame Channeler.
        this.getLeftHalfCard().addAbility(new SpellControlledDealsDamageTriggeredAbility(Zone.BATTLEFIELD,
                new TransformSourceEffect(), StaticFilters.FILTER_SPELL, false
        ).setTriggerPhrase("When a spell you control deals damage, "));

        // Embodiment of Flame
        this.getRightHalfCard().setPT(3, 3);

        // Whenever a spell you control deals damage, put a flame counter on Embodiment of Flame.
        this.getRightHalfCard().addAbility(new SpellControlledDealsDamageTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.FLAME.createInstance()),
                StaticFilters.FILTER_SPELL, false
        ));

        // {1}, Remove a flame counter from Embodiment of Flame: Exile the top card of your library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn),
                new GenericManaCost(1)
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.FLAME.createInstance()));
        this.getRightHalfCard().addAbility(ability);
    }

    private FlameChanneler(final FlameChanneler card) {
        super(card);
    }

    @Override
    public FlameChanneler copy() {
        return new FlameChanneler(this);
    }
}
