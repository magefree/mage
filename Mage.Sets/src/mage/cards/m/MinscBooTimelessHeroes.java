package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BooToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinscBooTimelessHeroes extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with trample or haste");

    static {
        filter.add(Predicates.or(
                new AbilityPredicate(TrampleAbility.class),
                new AbilityPredicate(HasteAbility.class)
        ));
    }

    public MinscBooTimelessHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINSC);
        this.setStartingLoyalty(3);

        // When Minsc & Boo, Timeless Heroes enters the battlefield and at the beginning of your upkeep, you may create Boo, a legendary 1/1 red Hamster creature token with trample and haste.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new BooToken()), true,
                "When {this} enters the battlefield and at the beginning of your upkeep, ",
                new EntersBattlefieldTriggeredAbility(null, false),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false)
        ));

        // +1: Put three +1/+1 counters on up to one target creature with trample or haste.
        Ability ability = new LoyaltyAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)), 1
        );
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // −2: Sacrifice a creature. When you do, Minsc & Boo, Timeless Heroes deals X damage to any target, where X is that creature's power. If the sacrificed creature was a Hamster, draw X cards.
        this.addAbility(new LoyaltyAbility(new MinscBooTimelessHeroesEffect(), -2));

        // Minsc & Boo, Timeless Heroes can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private MinscBooTimelessHeroes(final MinscBooTimelessHeroes card) {
        super(card);
    }

    @Override
    public MinscBooTimelessHeroes copy() {
        return new MinscBooTimelessHeroes(this);
    }
}

class MinscBooTimelessHeroesEffect extends OneShotEffect {

    MinscBooTimelessHeroesEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice a creature. When you do, {this} deals X damage to any target, " +
                "where X is that creature's power. If the sacrificed creature was a Hamster, draw X cards";
    }

    private MinscBooTimelessHeroesEffect(final MinscBooTimelessHeroesEffect effect) {
        super(effect);
    }

    @Override
    public MinscBooTimelessHeroesEffect copy() {
        return new MinscBooTimelessHeroesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        int power = permanent.getPower().getValue();
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(power), false, "deals X damage to any target, " +
                "where X is that creature's power. If the sacrificed creature was a Hamster, draw X cards"
        );
        if (permanent.hasSubtype(SubType.HAMSTER, game)) {
            ability.addEffect(new DrawCardSourceControllerEffect(power));
        }
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
