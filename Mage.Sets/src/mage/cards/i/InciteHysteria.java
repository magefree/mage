
package mage.cards.i;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
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
public final class InciteHysteria extends CardImpl {

    public InciteHysteria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Radiance - Until end of turn, target creature and each other creature that shares a color with it gain "This creature can't block."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new InciteHysteriaEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.RADIANCE);
    }

    private InciteHysteria(final InciteHysteria card) {
        super(card);
    }

    @Override
    public InciteHysteria copy() {
        return new InciteHysteria(this);
    }
}

class InciteHysteriaEffect extends OneShotEffect {

    public InciteHysteriaEffect() {
        super(Outcome.AddAbility);
        this.staticText = "Until end of turn, target creature and each other creature that shares a color with it gain \"This creature can't block.\"";
    }

    public InciteHysteriaEffect(final InciteHysteriaEffect effect) {
        super(effect);
    }

    @Override
    public InciteHysteriaEffect copy() {
        return new InciteHysteriaEffect(this);
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
                        ContinuousEffect effect = new GainAbilityTargetEffect(new CantBlockAbility(), Duration.EndOfTurn);
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
