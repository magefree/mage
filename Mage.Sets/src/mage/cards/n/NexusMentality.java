package mage.cards.n;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class NexusMentality extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another target nonland permanent you control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new AnotherTargetPredicate(2));
    }

    public NexusMentality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Choose one. If you control a commander as you cast this spell, you may choose both instead.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both instead."
        );
        this.getSpellAbility().getModes().setMoreCondition(2, ControlACommanderCondition.instance);

        // * Move all counters from target nonland permanent you control onto another target nonland permanent you control.
        this.getSpellAbility().addEffect(new NexusMentalityMoveEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND)
            .withChooseHint("to move counters from").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(filter)
            .withChooseHint("to move counters to").setTargetTag(2));

        // * Remove all counters from target nonland permanent you control. Draw a card for each counter removed this way.
    this.getSpellAbility().addMode(new Mode(new NexusMentalityDrawEffect())
            .addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND)));
    }

    private NexusMentality(final NexusMentality card) {
        super(card);
    }

    @Override
    public NexusMentality copy() {
        return new NexusMentality(this);
    }
}

class NexusMentalityMoveEffect extends OneShotEffect {

    NexusMentalityMoveEffect() {
        super(Outcome.Neutral);
        staticText = "Move all counters from target nonland permanent you control onto another target nonland permanent you control";
    }

    private NexusMentalityMoveEffect(final NexusMentalityMoveEffect effect) {
        super(effect);
    }

    @Override
    public NexusMentalityMoveEffect copy() {
        return new NexusMentalityMoveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent from = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent to = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (from == null || to == null) {
            return false;
        }
        Permanent copy = from.copy();
        for (Counter counter : copy.getCounters(game).values()) {
            from.removeCounters(counter, source, game);
            to.addCounters(counter, source.getControllerId(), source, game);
        }
        return true;
    }
}

class NexusMentalityDrawEffect extends OneShotEffect {

    NexusMentalityDrawEffect() {
        super(Outcome.DrawCard);
        staticText = "Remove all counters from target nonland permanent you control. Draw a card for each counter removed this way";
    }

    private NexusMentalityDrawEffect(final NexusMentalityDrawEffect effect) {
        super(effect);
    }

    @Override
    public NexusMentalityDrawEffect copy() {
        return new NexusMentalityDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || permanent == null) {
            return false;
        }
        int countersRemoved = permanent.removeAllCounters(source, game);
        if (countersRemoved > 0) {
            controller.drawCards(countersRemoved, source, game);
        }
        return true;
    }
}
