package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureAllEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author NinthWorld
 */
public final class EMPRound extends CardImpl {

    public EMPRound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");
        

        // Turn target nonland permanent face down.
        this.getSpellAbility().addEffect(new EMPRoundEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    public EMPRound(final EMPRound card) {
        super(card);
    }

    @Override
    public EMPRound copy() {
        return new EMPRound(this);
    }
}

// From BackslideEffect
class EMPRoundEffect extends OneShotEffect {

    EMPRoundEffect() {
        super(Outcome.Benefit);
        this.staticText = "Turn target nonland permanent face down.";
    }

    EMPRoundEffect(final EMPRoundEffect effect) {
        super(effect);
    }

    @Override
    public EMPRoundEffect copy() {
        return new EMPRoundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Predicate pred = new PermanentIdPredicate(UUID.randomUUID());
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                pred = Predicates.or(pred, new PermanentIdPredicate(targetId));
            }
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(pred);
        game.addEffect(new BecomesFaceDownCreatureAllEffect(filter), source);
        return true;
    }
}
