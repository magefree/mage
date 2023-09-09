
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpecialAction;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateSpecialActionEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.RemoveSpecialActionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author MTGfan
 */
public final class GuardianAngel extends CardImpl {

    public GuardianAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}");

        // Prevent the next X damage that would be dealt to any target this turn. Until end of turn, you may pay {1} any time you could cast an instant. If you do, prevent the next 1 damage that would be dealt to that creature or player this turn.
        this.getSpellAbility().addEffect(new GuardianAngelEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private GuardianAngel(final GuardianAngel card) {
        super(card);
    }

    @Override
    public GuardianAngel copy() {
        return new GuardianAngel(this);
    }
}

class GuardianAngelEffect extends OneShotEffect {

    public GuardianAngelEffect() {
        super(Outcome.Benefit);
        this.staticText = "Prevent the next X damage that would be dealt to any target this turn. Until end of turn, you may pay {1} any time you could cast an instant. If you do, prevent the next 1 damage that would be dealt to that permanent or player this turn";
    }

    private GuardianAngelEffect(final GuardianAngelEffect effect) {
        super(effect);
    }

    @Override
    public GuardianAngelEffect copy() {
        return new GuardianAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            String targetName = "";
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer == null) {
                Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (targetPermanent == null) {
                    return true;
                }
                targetName = targetPermanent.getIdName();
            } else {
                targetName = "player " + targetPlayer.getName();
            }
            ContinuousEffect effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, source.getManaCostsToPay().getX(), false);
            effect.setTargetPointer(getTargetPointer());
            game.addEffect(effect, source);
            SpecialAction specialAction = new GuardianAngelAction();
            specialAction.getEffects().get(0).setTargetPointer(getTargetPointer());
            specialAction.getEffects().get(0).setText("Prevent the next 1 damage that would be dealt to any target this turn (" + targetName + ").");
            new CreateSpecialActionEffect(specialAction).apply(game, source);
            // Create a hidden delayed triggered ability to remove the special action at end of turn.
            new CreateDelayedTriggeredAbilityEffect(new GuardianAngelDelayedTriggeredAbility(specialAction.getId()), false).apply(game, source);
            return true;

        }
        return false;
    }
}

class GuardianAngelAction extends SpecialAction {

    GuardianAngelAction() {
        super();
        this.addCost(new GenericManaCost(1));
        this.addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 1));
    }

    private GuardianAngelAction(final GuardianAngelAction ability) {
        super(ability);
    }

    @Override
    public GuardianAngelAction copy() {
        return new GuardianAngelAction(this);
    }
}

class GuardianAngelDelayedTriggeredAbility extends DelayedTriggeredAbility {

    GuardianAngelDelayedTriggeredAbility(UUID specialActionId) {
        super(new RemoveSpecialActionEffect(specialActionId), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    private GuardianAngelDelayedTriggeredAbility(final GuardianAngelDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GuardianAngelDelayedTriggeredAbility copy() {
        return new GuardianAngelDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLEANUP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
