package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TreasureMap extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.TREASURE, "Treasure");

    public TreasureMap(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}",
                "Treasure Cove",
                new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // {1}, {T}: Scry 1. Put a landmark counter on Treasure Map. Then if there are three or more landmark counters on it, remove those counters, transform Treasure Map, and create three colorless Treasure artifact tokens with "{T}, Sacrifice this artifact: Add one mana of any color."
        Ability ability = new SimpleActivatedAbility(
                new ScryEffect(1, false), new GenericManaCost(1)
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.LANDMARK.createInstance()));
        ability.addEffect(new TreasureMapEffect());
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // Treasure Cove
        // (Transforms from Treasure Map.)
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Treasure Map.)</i>")));

        // {T}: Add {C}.
        this.getRightHalfCard().addAbility(new ColorlessManaAbility());

        // {T}, Sacrifice a Treasure: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        this.getRightHalfCard().addAbility(ability);
    }

    private TreasureMap(final TreasureMap card) {
        super(card);
    }

    @Override
    public TreasureMap copy() {
        return new TreasureMap(this);
    }
}

class TreasureMapEffect extends OneShotEffect {

    TreasureMapEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if there are three or more landmark counters on it, "
                + "remove those counters, transform {this}, and create three Treasure tokens";
    }

    TreasureMapEffect(final TreasureMapEffect effect) {
        super(effect);
    }

    @Override
    public TreasureMapEffect copy() {
        return new TreasureMapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        int counters = permanent.getCounters(game).getCount(CounterType.LANDMARK);
        if (counters > 2) {
            permanent.removeCounters("landmark", counters, source, game);
            permanent.transform(source, game);
            new TreasureToken().putOntoBattlefield(3, game, source);
        }
        return true;
    }
}
