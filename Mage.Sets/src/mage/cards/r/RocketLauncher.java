package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RocketLauncher extends CardImpl {

    public RocketLauncher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}: Rocket Launcher deals 1 damage to any target. Destroy Rocket Launcher at the beginning of the next end step. Activate this ability only if you've controlled Rocket Launcher continuously since the beginning of your most recent turn.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(1),
                new GenericManaCost(2), RocketLauncherCondition.instance);
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new DestroySourceEffect(true))
        ).setText("destroy {this} at the beginning of the next end step"));
        this.addAbility(ability);
    }

    private RocketLauncher(final RocketLauncher card) {
        super(card);
    }

    @Override
    public RocketLauncher copy() {
        return new RocketLauncher(this);
    }
}

enum RocketLauncherCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.wasControlledFromStartOfControllerTurn();
    }

    @Override
    public String toString() {
        return "you've controlled {this} continuously since the beginning of your most recent turn";
    }
}
