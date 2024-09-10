
package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;


/**
 * @author Pete Rossi
 */
public final class ChimericEgg extends CardImpl {

    private static final FilterSpell nonArtifactFilter = new FilterSpell("a nonartifact spell");

    static {
        nonArtifactFilter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public ChimericEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever an opponent casts a nonartifact spell, put a charge counter on Chimeric Egg.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), nonArtifactFilter, false));

        // Remove three charge counters from Chimeric Egg: Chimeric Egg becomes a 6/6 Construct artifact creature with trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(6, 6, "6/6 Construct artifact creature with trample")
                        .withSubType(SubType.CONSTRUCT)
                        .withType(CardType.ARTIFACT)
                        .withAbility(TrampleAbility.getInstance()),
                CardType.ARTIFACT, Duration.EndOfTurn), new RemoveCountersSourceCost(new Counter(CounterType.CHARGE.getName(), 3))));
    }

    private ChimericEgg(final ChimericEgg card) {
        super(card);
    }

    @Override
    public ChimericEgg copy() {
        return new ChimericEgg(this);
    }
}
