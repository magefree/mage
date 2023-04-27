package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AstralDrift extends CardImpl {

    public AstralDrift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever you cycle Astral Drift or cycle another card while Astral Drift is on the battlefield, you may exile target creature. If you do, return that card to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new AstralDriftTriggeredAbility());

        // Cycling {2}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{W}")));
    }

    private AstralDrift(final AstralDrift card) {
        super(card);
    }

    @Override
    public AstralDrift copy() {
        return new AstralDrift(this);
    }
}

class AstralDriftTriggeredAbility extends TriggeredAbilityImpl {

    AstralDriftTriggeredAbility() {
        super(Zone.ALL, new AstralDriftEffect(), true);
        this.addTarget(new TargetCreaturePermanent());
    }

    private AstralDriftTriggeredAbility(final AstralDriftTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getState().getStack().isEmpty()) {
            return false;
        }
        StackObject item = game.getState().getStack().getFirst();
        if (!(item instanceof StackAbility
                && item.getStackAbility() instanceof CyclingAbility)) {
            return false;
        }
        if (event.getSourceId().equals(this.getSourceId())) {
            return true;
        }
        if (game.getPermanent(getSourceId()) == null || !event.getPlayerId().equals(controllerId)) {
            return false;
        }
        return true;
    }

    @Override
    public AstralDriftTriggeredAbility copy() {
        return new AstralDriftTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you cycle {this} or cycle another card while {this} is on the battlefield, " +
                "you may exile target creature. If you do, return that card to the battlefield " +
                "under its owner's control at the beginning of the next end step.";
    }
}

class AstralDriftEffect extends OneShotEffect {

    AstralDriftEffect() {
        super(Outcome.Detriment);
    }

    private AstralDriftEffect(final AstralDriftEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return true;
        }
        UUID exileId = UUID.randomUUID();
        if (!controller.moveCardsToExile(permanent, source, game, true, exileId, sourceObject.getIdName())) {
            return true;
        }
        //create delayed triggered ability
        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
        effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }

    @Override
    public AstralDriftEffect copy() {
        return new AstralDriftEffect(this);
    }
}
