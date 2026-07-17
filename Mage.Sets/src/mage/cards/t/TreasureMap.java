package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreasureMap extends TransformingDoubleFacedCard {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.LANDMARK, 3);
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Treasure");

    static {
        filter.add(SubType.TREASURE.getPredicate());
    }

    public TreasureMap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}",
                "Treasure Cove",
                new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Treasure Map
        // {1}, {T}: Scry 1. Put a landmark counter on Treasure Map. Then if there are three or more landmark counters on it, remove those counters, transform Treasure Map, and create three colorless Treasure artifact tokens with "{T}, Sacrifice this artifact: Add one mana of any color."
        Ability ability = new SimpleActivatedAbility(new ScryEffect(1, false), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersSourceEffect(CounterType.LANDMARK.createInstance()));
        ability.addEffect(new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.LANDMARK), condition, "Then if there are three or " +
                "more landmark counters on it, remove those counters, transform {this}, and create three Treasure tokens"
        ).addEffect(new TransformSourceEffect()).addEffect(new CreateTokenEffect(new TreasureToken(), 3)));
        this.getLeftHalfCard().addAbility(ability);

        // Treasure Cove
        // {T}: Add {C}.
        this.getRightHalfCard().addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice a Treasure: Draw a card.
        Ability ability2 = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(filter));
        this.getRightHalfCard().addAbility(ability2);
    }

    private TreasureMap(final TreasureMap card) {
        super(card);
    }

    @Override
    public TreasureMap copy() {
        return new TreasureMap(this);
    }
}
