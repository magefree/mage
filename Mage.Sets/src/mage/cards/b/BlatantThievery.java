
package mage.cards.b;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class BlatantThievery extends CardImpl {

    public BlatantThievery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}{U}");

        // For each opponent, gain control of target permanent that player controls.
        this.getSpellAbility().addEffect(new BlatantThieveryEffect());
    }

    public BlatantThievery(final BlatantThievery card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    FilterPermanent filter = new FilterPermanent("Permanent of player " + opponent.getName());
                    filter.add(new ControllerIdPredicate(opponentId));
                    TargetPermanent targetPermanent = new TargetPermanent(filter);
                    ability.addTarget(targetPermanent);
                }
            }
        }
    }

    @Override
    public BlatantThievery copy() {
        return new BlatantThievery(this);
    }
}

class BlatantThieveryEffect extends OneShotEffect {

    BlatantThieveryEffect() {
        super(Outcome.GainControl);
        this.staticText = "For each opponent, gain control of target permanent that player controls";
    }

    BlatantThieveryEffect(final BlatantThieveryEffect effect) {
        super(effect);
    }

    @Override
    public BlatantThieveryEffect copy() {
        return new BlatantThieveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Target target : source.getTargets()) {
            if (target.getFirstTarget() != null) {
                ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame);
                effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                game.addEffect(effect, source);
            }
        }
        return true;
    }
}
