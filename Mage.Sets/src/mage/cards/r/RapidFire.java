package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.BeforeBlockersAreDeclaredCondition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class RapidFire extends CardImpl {

    public RapidFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Cast this spell only before blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null, BeforeBlockersAreDeclaredCondition.instance, "Cast this spell only before blockers are declared"));

        // Target creature gains first strike until end of turn. If it doesn’t have rampage, that creature gains rampage 2 until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new RapidFireEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private RapidFire(final RapidFire card) {
        super(card);
    }

    @Override
    public RapidFire copy() {
        return new RapidFire(this);
    }

}

class RapidFireEffect extends OneShotEffect {

    public RapidFireEffect() {
        super(Outcome.AddAbility);
        this.staticText = "If it doesn't have rampage, that creature gains rampage 2 until end of turn";
    }

    private RapidFireEffect(final RapidFireEffect effect) {
        super(effect);
    }

    @Override
    public RapidFireEffect copy() {
        return new RapidFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller != null && permanent != null) {
            if (!permanent.getAbilities().containsClass(RampageAbility.class)) {
                ContinuousEffect effect = new GainAbilityTargetEffect(new RampageAbility(2), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(permanent, game));
                game.addEffect(effect, source);
            }
        }
        return false;
    }
}
