package mage.cards.t;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 * @author Cguy7777
 */
public final class TheNiptonLottery extends CardImpl {

    public TheNiptonLottery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Choose a creature at random.
        // You gain control of that creature until end of turn. Untap it. It gains haste until end of turn.
        // Then destroy all other creatures.
        this.getSpellAbility().addEffect(new TheNiptonLotteryEffect());
    }

    private TheNiptonLottery(final TheNiptonLottery card) {
        super(card);
    }

    @Override
    public TheNiptonLottery copy() {
        return new TheNiptonLottery(this);
    }
}

class TheNiptonLotteryEffect extends OneShotEffect {

    TheNiptonLotteryEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a creature at random. You gain control of that creature until end of turn. " +
                "Untap it. It gains haste until end of turn. Then destroy all other creatures.";
    }

    private TheNiptonLotteryEffect(final TheNiptonLotteryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Choose a creature at random.
        List<Permanent> creatureList = game.getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        if (creatureList.isEmpty()) {
            return true;
        }
        Permanent permanentToSteal = creatureList.get(RandomUtil.nextInt(creatureList.size()));

        // You gain control of that creature until end of turn. Untap it. It gains haste until end of turn.
        ContinuousEffect controlEffect = new GainControlTargetEffect(Duration.EndOfTurn);
        controlEffect.setTargetPointer(new FixedTarget(permanentToSteal, game));
        game.addEffect(controlEffect, source);
        game.processAction();
        permanentToSteal.untap(game);
        ContinuousEffect hasteEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
        hasteEffect.setTargetPointer(new FixedTarget(permanentToSteal, game));
        game.addEffect(hasteEffect, source);

        // Then destroy all other creatures.
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.not(new PermanentIdPredicate(permanentToSteal.getId())));
        return new DestroyAllEffect(filter).apply(game, source);
    }

    @Override
    public TheNiptonLotteryEffect copy() {
        return new TheNiptonLotteryEffect(this);
    }
}
