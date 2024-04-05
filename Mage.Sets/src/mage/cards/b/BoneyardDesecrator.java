package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class BoneyardDesecrator extends CardImpl {

    public BoneyardDesecrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // {1}{B}, Sacrifice another creature: Put a +1/+1 counter on Boneyard Desecrator. If an outlaw was sacrificed this way, create a Treasure token.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addEffect(new BoneyardDesecratorEffect());
        this.addAbility(ability);
    }

    private BoneyardDesecrator(final BoneyardDesecrator card) {
        super(card);
    }

    @Override
    public BoneyardDesecrator copy() {
        return new BoneyardDesecrator(this);
    }
}

// Inspired by Thallid Omnivore
class BoneyardDesecratorEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("an outlaw");

    static {
        filter.add(OutlawPredicate.instance);
    }

    BoneyardDesecratorEffect() {
        super(Outcome.GainLife);
        this.staticText = "If an outlaw was sacrificed this way, create a Treasure token";
    }

    private BoneyardDesecratorEffect(final BoneyardDesecratorEffect effect) {
        super(effect);
    }

    @Override
    public BoneyardDesecratorEffect copy() {
        return new BoneyardDesecratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                List<Permanent> permanents = sacrificeCost.getPermanents();
                for (Permanent permanent : permanents) {
                    if (!filter.match(permanent, source.getControllerId(), source, game)) {
                        continue;
                    }
                    return new CreateTokenEffect(new TreasureToken()).apply(game, source);
                }
            }
        }
        return false;
    }
}
