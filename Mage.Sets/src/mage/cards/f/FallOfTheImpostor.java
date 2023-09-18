package mage.cards.f;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class FallOfTheImpostor extends CardImpl {

    public FallOfTheImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II — Put a +1/+1 counter on up to one target creature.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                new TargetCreaturePermanent(0, 1)
        );

        // III — Exile a creature with the greatest power among creatures target opponent controls.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new FallOfTheImpostorEffect(), new TargetOpponent()
        );
        this.addAbility(sagaAbility);
    }

    private FallOfTheImpostor(final FallOfTheImpostor card) {
        super(card);
    }

    @Override
    public FallOfTheImpostor copy() {
        return new FallOfTheImpostor(this);
    }
}

class FallOfTheImpostorEffect extends OneShotEffect {

    public FallOfTheImpostorEffect() {
        super(Outcome.Exile);
        staticText = "Exile a creature with the greatest power among creatures target opponent controls";
    }

    private FallOfTheImpostorEffect(final FallOfTheImpostorEffect effect) {
        super(effect);
    }

    @Override
    public FallOfTheImpostorEffect copy() {
        return new FallOfTheImpostorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller != null && opponent != null) {
            List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(
                    StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game
            );
            Integer maxPower = null;
            for (Permanent permanent : permanents) {
                if (permanent != null) {
                    int power = permanent.getPower().getValue();
                    if (maxPower == null || power > maxPower) {
                        maxPower = power;
                    }
                }
            }
            if (maxPower != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new ControllerIdPredicate(opponent.getId()));
                filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, maxPower));
                TargetCreaturePermanent target = new TargetCreaturePermanent(1, 1, filter, true);
                controller.chooseTarget(outcome, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    controller.moveCardsToExile(permanent, source, game, true, null, null);
                    return true;
                }
            }
        }
        return false;
    }
}
