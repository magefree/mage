package mage.cards.l;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LevitatingStatue extends CardImpl {

    public LevitatingStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a noncreature spell, put a +1/+1 counter on Levitating Statue.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // {2}: Levitating Statue becomes a 1/1 Construct artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        1, 1, "1/1 Construct artifact creature", SubType.CONSTRUCT
                ).withType(CardType.ARTIFACT), "", Duration.EndOfTurn
        ), new GenericManaCost(2)));
    }

    private LevitatingStatue(final LevitatingStatue card) {
        super(card);
    }

    @Override
    public LevitatingStatue copy() {
        return new LevitatingStatue(this);
    }
}
