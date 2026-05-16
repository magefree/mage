package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NexusMentality extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("nonland permanent you control");
    private static final FilterControlledPermanent anotherFilter
            = new FilterControlledPermanent("another target nonland permanent you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        anotherFilter.add(Predicates.not(CardType.LAND.getPredicate()));
        anotherFilter.add(new AnotherTargetPredicate(2));
    }

    public NexusMentality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Choose one. If you control a commander as you cast this spell, you may choose both instead.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // * Move all counters from target nonland permanent you control onto another target nonland permanent you control.
        this.getSpellAbility().addEffect(new NexusMentalityMoveCountersEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent(filter)
                .withChooseHint("to move counters from").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(anotherFilter)
                .withChooseHint("to move counters to").setTargetTag(2));

        // * Remove all counters from target nonland permanent you control. Draw a card for each counter removed this way.
        Mode mode = new Mode(new NexusMentalityRemoveCountersEffect());
        mode.addTarget(new TargetControlledPermanent(filter)
                .withChooseHint("to remove counters from and draw"));
        this.getSpellAbility().addMode(mode);
    }

    private NexusMentality(final NexusMentality card) {
        super(card);
    }

    @Override
    public NexusMentality copy() {
        return new NexusMentality(this);
    }
}

class NexusMentalityMoveCountersEffect extends OneShotEffect {

    NexusMentalityMoveCountersEffect() {
        super(Outcome.Benefit);
        staticText = "move all counters from target nonland permanent you control " +
                "onto another target nonland permanent you control";
    }

    private NexusMentalityMoveCountersEffect(final NexusMentalityMoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public NexusMentalityMoveCountersEffect copy() {
        return new NexusMentalityMoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent fromPermanent = game.getPermanent(source.getModes().getMode().getTargets().get(0).getFirstTarget());
        Permanent toPermanent = game.getPermanent(source.getModes().getMode().getTargets().get(1).getFirstTarget());
        if (fromPermanent == null || toPermanent == null) {
            return false;
        }
        for (Counter counter : fromPermanent.copy().getCounters(game).values()) {
            fromPermanent.removeCounters(counter, source, game);
            toPermanent.addCounters(counter, source.getControllerId(), source, game);
        }
        return true;
    }
}

class NexusMentalityRemoveCountersEffect extends OneShotEffect {

    NexusMentalityRemoveCountersEffect() {
        super(Outcome.DrawCard);
        staticText = "remove all counters from target nonland permanent you control. " +
                "Draw a card for each counter removed this way";
    }

    private NexusMentalityRemoveCountersEffect(final NexusMentalityRemoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public NexusMentalityRemoveCountersEffect copy() {
        return new NexusMentalityRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getModes().getMode().getTargets().get(0).getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        int counterCount = 0;
        for (Counter counter : permanent.copy().getCounters(game).values()) {
            counterCount += counter.getCount();
            permanent.removeCounters(counter, source, game);
        }
        if (counterCount > 0) {
            player.drawCards(counterCount, source, game);
        }
        return true;
    }
}
