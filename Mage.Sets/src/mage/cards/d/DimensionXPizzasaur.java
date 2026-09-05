package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class DimensionXPizzasaur extends CardImpl {

    public DimensionXPizzasaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, put two +1/+1 counters on target creature. When you do, destroy up to one target creature with mana value less than or equal to the number of counters among permanents you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DimensionXPizzasaurEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}, {T}, Sacrifice this creature: You gain 3 life and each opponent loses 3 life.
        Ability ability2 = new SimpleActivatedAbility(new GainLifeEffect(3), new ManaCostsImpl<>("{2}"));
        ability2.addEffect(new LoseLifeOpponentsEffect(3).setText("and each opponent loses 3 life"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    private DimensionXPizzasaur(final DimensionXPizzasaur card) {
        super(card);
    }

    @Override
    public DimensionXPizzasaur copy() {
        return new DimensionXPizzasaur(this);
    }
}

class DimensionXPizzasaurEffect extends OneShotEffect {

    DimensionXPizzasaurEffect() {
        super(Outcome.Benefit);
        staticText = "put two +1/+1 counters on target creature. "
            + "When you do, destroy up to one target creature with mana value less than or equal "
            + "to the number of counters among permanents you control";
    }

    private DimensionXPizzasaurEffect(final DimensionXPizzasaurEffect effect) {
        super(effect);
    }

    @Override
    public DimensionXPizzasaurEffect copy() {
        return new DimensionXPizzasaurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // put two +1/+1 counters on target creature
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetCreature == null || !targetCreature.addCounters(CounterType.P1P1.createInstance(2), source, game)) {
            return false;
        }

        // the number of counters among permanents you control
        int counters = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            counters += permanent.getCounters(game).values().stream().mapToInt(c -> c.getCount()).sum();
        }

        FilterPermanent filter = new FilterCreaturePermanent("creature with mana value " + counters + " or less");
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, counters));

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(0, 1, filter));
        game.fireReflexiveTriggeredAbility(ability, source);

        return true;
    }
}
