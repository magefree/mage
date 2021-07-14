
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33, Unknown (Glimpse of Nature), LevelX2 (Armadillo Cloak)
 */
public final class Spiritualize extends CardImpl {

    public Spiritualize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Until end of turn, whenever target creature deals damage, you gain that much life.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new SpiritualizeTriggeredAbility()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private Spiritualize(final Spiritualize card) {
        super(card);
    }

    @Override
    public Spiritualize copy() {
        return new Spiritualize(this);
    }
}

class SpiritualizeTriggeredAbility extends DelayedTriggeredAbility {

    public SpiritualizeTriggeredAbility() {
        super(new SpiritualizeEffect(), Duration.EndOfTurn, false);
    }

    public SpiritualizeTriggeredAbility(final SpiritualizeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpiritualizeTriggeredAbility copy() {
        return new SpiritualizeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent target = game.getPermanent(this.getFirstTarget());
        if (target != null && event.getSourceId().equals(target.getId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever target creature deals damage, " ;
    }
}

class SpiritualizeEffect extends OneShotEffect {

    public SpiritualizeEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain that much life";
    }

    public SpiritualizeEffect(final SpiritualizeEffect effect) {
        super(effect);
    }

    @Override
    public SpiritualizeEffect copy() {
        return new SpiritualizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                controller.gainLife(amount, game, source);
                return true;
            }
        }
        return false;
    }
}
