package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellControlledDealsDamageTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class EmbodimentOfFlame extends CardImpl {

    public EmbodimentOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        this.nightCard = true;

        // Whenever a spell you control deals damage, put a flame counter on Embodiment of Flame.
        this.addAbility(new SpellControlledDealsDamageTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.FLAME.createInstance()),
                StaticFilters.FILTER_SPELL, false
        ));

        // {1}, Remove a flame counter from Embodiment of Flame: Exile the top card of your library. You may play that card this turn.
        Ability ability = new SimpleActivatedAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn),
                new GenericManaCost(1)
        );
        ability.addCost(new RemoveCountersSourceCost(CounterType.FLAME.createInstance()));
        this.addAbility(ability);
    }

    private EmbodimentOfFlame(final EmbodimentOfFlame card) {
        super(card);
    }

    @Override
    public EmbodimentOfFlame copy() {
        return new EmbodimentOfFlame(this);
    }
}
