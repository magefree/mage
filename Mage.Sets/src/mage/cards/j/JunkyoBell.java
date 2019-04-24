
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX
 */
public final class JunkyoBell extends CardImpl {

    public JunkyoBell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your upkeep, you may have target creature you control get +X/+X until end of turn,
        // where X is the number of creatures you control. If you do, sacrifice that creature at the beginning of the next end step.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent());
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new BoostTargetEffect(amount, amount, Duration.EndOfTurn, true), TargetController.YOU, true);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new JunkyoBellSacrificeEffect());
        this.addAbility(ability);
    }

    public JunkyoBell(final JunkyoBell card) {
        super(card);
    }

    @Override
    public JunkyoBell copy() {
        return new JunkyoBell(this);
    }

    private static class JunkyoBellSacrificeEffect extends OneShotEffect {

        public JunkyoBellSacrificeEffect() {
            super(Outcome.Sacrifice);
            this.staticText = "If you do, sacrifice that creature at the beginning of the next end step";
        }

        public JunkyoBellSacrificeEffect(final JunkyoBellSacrificeEffect effect) {
            super(effect);
        }

        @Override
        public JunkyoBellSacrificeEffect copy() {
            return new JunkyoBellSacrificeEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent creature = game.getPermanent(source.getFirstTarget());
            if (creature != null) {
                SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice boosted " + creature.getName(), source.getControllerId());
                sacrificeEffect.setTargetPointer(new FixedTarget(creature, game));
                DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                game.addDelayedTriggeredAbility(delayedAbility, source);
                return true;
            }
            return false;
        }
    }
}
