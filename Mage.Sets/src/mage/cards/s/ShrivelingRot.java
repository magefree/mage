
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EntwineAbility;
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

import java.util.UUID;

/**
 * @author L_J
 */
public final class ShrivelingRot extends CardImpl {

    public ShrivelingRot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Choose one -
        // Until end of turn, whenever a creature is dealt damage, destroy it.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new ShrivelingRotDestroyTriggeredAbility()));

        // Until end of turn, whenever a creature dies, that creature's controller loses life equal to its toughness.
        Mode mode = new Mode(new CreateDelayedTriggeredAbilityEffect(new ShrivelingRotLoseLifeTriggeredAbility()));
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {2}{B}
        this.addAbility(new EntwineAbility("{2}{B}"));
    }

    private ShrivelingRot(final ShrivelingRot card) {
        super(card);
    }

    @Override
    public ShrivelingRot copy() {
        return new ShrivelingRot(this);
    }
}

class ShrivelingRotDestroyTriggeredAbility extends DelayedTriggeredAbility {

    ShrivelingRotDestroyTriggeredAbility() {
        super(new DestroyTargetEffect(), Duration.EndOfTurn, false);
    }

    ShrivelingRotDestroyTriggeredAbility(final ShrivelingRotDestroyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShrivelingRotDestroyTriggeredAbility copy() {
        return new ShrivelingRotDestroyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a creature is dealt damage, destroy it.";
    }
}

class ShrivelingRotLoseLifeTriggeredAbility extends DelayedTriggeredAbility {

    ShrivelingRotLoseLifeTriggeredAbility() {
        super(new ShrivelingRotEffect(), Duration.EndOfTurn, false);
    }

    ShrivelingRotLoseLifeTriggeredAbility(final ShrivelingRotLoseLifeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShrivelingRotLoseLifeTriggeredAbility copy() {
        return new ShrivelingRotLoseLifeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            if (zEvent.getTarget().isCreature(game)) {
                Effect effect = this.getEffects().get(0);
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a creature dies, that creature's controller loses life equal to its toughness.";
    }
}

class ShrivelingRotEffect extends OneShotEffect {

    public ShrivelingRotEffect() {
        super(Outcome.LoseLife);
        staticText = "that creature's controller loses life equal to its toughness";
    }

    public ShrivelingRotEffect(final ShrivelingRotEffect effect) {
        super(effect);
    }

    @Override
    public ShrivelingRotEffect copy() {
        return new ShrivelingRotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) {
            if (permanent.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(permanent.getId())
                    && game.getState().getZone(permanent.getId()) != Zone.GRAVEYARD) {
                // A replacement effect has moved the card to another zone as graveyard
                return true;
            }
            Player permanentController = game.getPlayer(permanent.getControllerId());
            if (permanentController != null) {
                int amount = permanent.getToughness().getValue();
                permanentController.loseLife(amount, game, source, false);
                return true;
            }
        }
        return false;
    }
}
