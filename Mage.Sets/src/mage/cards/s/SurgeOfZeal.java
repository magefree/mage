
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class SurgeOfZeal extends CardImpl {

    public SurgeOfZeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Radiance - Target creature and each other creature that shares a color with it gain haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SurgeOfZealEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.RADIANCE);
    }

    private SurgeOfZeal(final SurgeOfZeal card) {
        super(card);
    }

    @Override
    public SurgeOfZeal copy() {
        return new SurgeOfZeal(this);
    }
}

class SurgeOfZealEffect extends OneShotEffect {

    public SurgeOfZealEffect() {
        super(Outcome.AddAbility);
        this.staticText = "Target creature and each other creature that shares a color with it gain haste until end of turn";
    }

    public SurgeOfZealEffect(final SurgeOfZealEffect effect) {
        super(effect);
    }

    @Override
    public SurgeOfZealEffect copy() {
        return new SurgeOfZealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (target != null) {
                ObjectColor color = target.getColor(game);
                for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                    if (permanent.getColor(game).shares(color)) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
