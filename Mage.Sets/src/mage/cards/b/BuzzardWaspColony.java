package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BuzzardWaspColony extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(CounterAnyPredicate.instance);
    }

    public BuzzardWaspColony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, you may sacrifice an artifact or creature. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE)
        )));

        // Whenever another creature you control dies, if it had counters on it, put its counters on this creature.
        this.addAbility(new DiesCreatureTriggeredAbility(new BuzzardWaspColonyEffect(), false, filter));
    }

    private BuzzardWaspColony(final BuzzardWaspColony card) {
        super(card);
    }

    @Override
    public BuzzardWaspColony copy() {
        return new BuzzardWaspColony(this);
    }
}

class BuzzardWaspColonyEffect extends OneShotEffect {

    BuzzardWaspColonyEffect() {
        super(Outcome.Benefit);
        staticText = "if it had counters on it, put its counters on {this}";
    }

    private BuzzardWaspColonyEffect(final BuzzardWaspColonyEffect effect) {
        super(effect);
    }

    @Override
    public BuzzardWaspColonyEffect copy() {
        return new BuzzardWaspColonyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = (Permanent) getValue("creatureDied");
        if (permanent == null || creature == null) {
            return false;
        }
        for (Counter counter : creature.getCounters(game).values()) {
            permanent.addCounters(counter.copy(), source, game);
        }
        return true;
    }
}
