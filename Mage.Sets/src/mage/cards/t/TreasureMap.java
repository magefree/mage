package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreasureMap extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.LANDMARK, 3);

    public TreasureMap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.secondSideCardClazz = mage.cards.t.TreasureCove.class;

        // {1}, {T}: Scry 1. Put a landmark counter on Treasure Map. Then if there are three or more landmark counters on it, remove those counters, transform Treasure Map, and create three colorless Treasure artifact tokens with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(new ScryEffect(1, false), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersSourceEffect(CounterType.LANDMARK.createInstance()));
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.LANDMARK), condition, "Then if there are three or " +
                "more landmark counters on it, remove those counters, transform {this}, and create three Treasure tokens"
        ).addEffect(new TransformSourceEffect()).addEffect(new CreateTokenEffect(new TreasureToken(), 3)));
        this.addAbility(ability);
    }

    private TreasureMap(final TreasureMap card) {
        super(card);
    }

    @Override
    public TreasureMap copy() {
        return new TreasureMap(this);
    }
}
