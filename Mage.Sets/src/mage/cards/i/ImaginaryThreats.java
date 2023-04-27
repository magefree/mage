
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.DontUntapInPlayersNextUntapStepAllEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class ImaginaryThreats extends CardImpl {

    public ImaginaryThreats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Creatures target opponent controls attack this turn if able. During that player's next untap step, creatures they control don't untap.
        getSpellAbility().addEffect(new ImaginaryThreatsEffect());
        getSpellAbility().addWatcher(new AttackedThisTurnWatcher());
        getSpellAbility().addTarget(new TargetOpponent());
        getSpellAbility().addEffect(new DontUntapInPlayersNextUntapStepAllEffect(new FilterCreaturePermanent())
                .setText("During that player's next untap step, creatures they control don't untap"));
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private ImaginaryThreats(final ImaginaryThreats card) {
        super(card);
    }

    @Override
    public ImaginaryThreats copy() {
        return new ImaginaryThreats(this);
    }
}

class ImaginaryThreatsEffect extends OneShotEffect {

    public ImaginaryThreatsEffect() {
        super(Outcome.Detriment);
        staticText = "Creatures target opponent controls attack this turn if able";
    }

    public ImaginaryThreatsEffect(final ImaginaryThreatsEffect effect) {
        super(effect);
    }

    @Override
    public ImaginaryThreatsEffect copy() {
        return new ImaginaryThreatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(player.getId()));
            RequirementEffect effect = new AttacksIfAbleAllEffect(filter, Duration.EndOfTurn);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
