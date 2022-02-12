package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author LevelX2
 */
public final class TearsOfRage extends CardImpl {

    public TearsOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // Cast Tears of Rage only during the declare attackers step.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(PhaseStep.DECLARE_ATTACKERS));

        // Attacking creatures you control get +X/+0 until end of turn, where X is the number of attacking creatures. Sacrifice those creatures at the beginning of the next end step.
        BoostControlledEffect effect = new BoostControlledEffect(new AttackingCreatureCount("the number of attacking creatures"), StaticValue.get(0),
                Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false, true);
        getSpellAbility().addEffect(effect);
        getSpellAbility().addEffect(new TearsOfRageEffect());
    }

    private TearsOfRage(final TearsOfRage card) {
        super(card);
    }

    @Override
    public TearsOfRage copy() {
        return new TearsOfRage(this);
    }
}

class TearsOfRageEffect extends OneShotEffect {

    public TearsOfRageEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Sacrifice those creatures at the beginning of the next end step";
    }

    public TearsOfRageEffect(final TearsOfRageEffect effect) {
        super(effect);
    }

    @Override
    public TearsOfRageEffect copy() {
        return new TearsOfRageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Effect effect = new SacrificeTargetEffect("Sacrifice those creatures at the beginning of the next end step", source.getControllerId());
            effect.setTargetPointer(new FixedTargets(game.getBattlefield().getAllActivePermanents(new FilterAttackingCreature(), controller.getId(), game), game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
            return true;
        }
        return false;
    }

}
