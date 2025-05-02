package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class CosmicIntervention extends CardImpl {

    public CosmicIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // If a permanent you control would be put into a graveyard from the battlefield this turn, exile it instead. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new CosmicInterventionReplacementEffect());

        // Foretell 1W
        this.addAbility(new ForetellAbility(this, "{1}{W}"));

    }

    private CosmicIntervention(final CosmicIntervention card) {
        super(card);
    }

    @Override
    public CosmicIntervention copy() {
        return new CosmicIntervention(this);
    }
}

class CosmicInterventionReplacementEffect extends ReplacementEffectImpl {

    CosmicInterventionReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "If a permanent you control would be put into a graveyard from the battlefield this turn, exile it instead. Return it to the battlefield under its owner's control at the beginning of the next end step";
    }

    private CosmicInterventionReplacementEffect(final CosmicInterventionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public CosmicInterventionReplacementEffect copy() {
        return new CosmicInterventionReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                Card card = game.getCard(event.getTargetId());
                if (card != null) {
                    ExileTargetForSourceEffect exileEffect = new ExileTargetForSourceEffect();
                    exileEffect.setTargetPointer(new FixedTarget(card.getId(), game));
                    exileEffect.apply(game, source);
                    Effect returnEffect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                    returnEffect.setTargetPointer(new FixedTarget(card.getId(), game));
                    AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(returnEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zoneChangeEvent = (ZoneChangeEvent) event;
        if (zoneChangeEvent.isDiesEvent()) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null
                    || controller == null
                    || permanent.getControllerId() == controller.getId()) {
                return true;
            }
        }
        return false;
    }
}
