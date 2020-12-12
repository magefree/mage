package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlamesOfTheRazeBoar extends CardImpl {

    public FlamesOfTheRazeBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{R}");

        // Flames of the Raze-Boar deals 4 damage to target creature an opponent controls. Then Flames of the Raze-Boar deals 2 damage to each other creature that player controls if you control a creature with power 4 or greater.
        this.getSpellAbility().addEffect(new FlamesOfTheRazeBoarEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private FlamesOfTheRazeBoar(final FlamesOfTheRazeBoar card) {
        super(card);
    }

    @Override
    public FlamesOfTheRazeBoar copy() {
        return new FlamesOfTheRazeBoar(this);
    }
}

class FlamesOfTheRazeBoarEffect extends OneShotEffect {

    FlamesOfTheRazeBoarEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 4 damage to target creature an opponent controls. " +
                "Then {this} deals 2 damage to each other creature that player controls " +
                "if you control a creature with power 4 or greater.";
    }

    private FlamesOfTheRazeBoarEffect(final FlamesOfTheRazeBoarEffect effect) {
        super(effect);
    }

    @Override
    public FlamesOfTheRazeBoarEffect copy() {
        return new FlamesOfTheRazeBoarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.damage(4, source.getSourceId(), source, game);
        if (!FerociousCondition.instance.apply(game, source)) {
            return true;
        }
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(permanent.getControllerId()));
        filter.add(Predicates.not(new PermanentIdPredicate(permanent.getId())));
        return new DamageAllEffect(2, filter).apply(game, source);
    }
}