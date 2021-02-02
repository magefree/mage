
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author ciaccona007
 */
public final class ManaVapors extends CardImpl {

    public ManaVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Lands target player controls don't untap during their next untap step.
        getSpellAbility().addTarget(new TargetPlayer());
        getSpellAbility().addEffect(new ManaVaporsEffect());
    }

    private ManaVapors(final ManaVapors card) {
        super(card);
    }

    @Override
    public ManaVapors copy() {
        return new ManaVapors(this);
    }
}

class ManaVaporsEffect extends OneShotEffect {

    ManaVaporsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Lands target player controls don't untap during their next untap step";
    }

    ManaVaporsEffect(final ManaVaporsEffect effect) {
        super(effect);
    }

    @Override
    public ManaVaporsEffect copy() {
        return new ManaVaporsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            FilterLandPermanent filter = new FilterLandPermanent();
            filter.add(new ControllerIdPredicate(targetPlayer.getId()));
            ContinuousEffect effect = new DontUntapInPlayersNextUntapStepAllEffect(filter);
            effect.setTargetPointer(new FixedTarget(targetPlayer.getId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
