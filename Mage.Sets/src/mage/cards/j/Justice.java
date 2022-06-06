package mage.cards.j;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Justice extends CardImpl {

    public Justice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");


        // At the beginning of your upkeep, sacrifice Justice unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{W}{W}")), TargetController.YOU, false));

        // Whenever a red creature or spell deals damage, Justice deals that much damage to that creature's or spell's controller.
        this.addAbility(new JusticeTriggeredAbility(new JusticeEffect()));
    }

    private Justice(final Justice card) {
        super(card);
    }

    @Override
    public Justice copy() {
        return new Justice(this);
    }
}

class JusticeTriggeredAbility extends TriggeredAbilityImpl {

    public JusticeTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public JusticeTriggeredAbility(final JusticeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public JusticeTriggeredAbility copy() {
        return new JusticeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(event.getSourceId());
        if (sourceObject != null && sourceObject.getColor(game).isRed()) {
            if (sourceObject instanceof Permanent && sourceObject.isCreature(game)
                    || sourceObject instanceof Spell) {
                this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                this.getEffects().get(0).setTargetPointer(new FixedTarget(game.getControllerId(sourceObject.getId())));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a red creature or spell deals damage, {this} deals that much damage to that creature's or spell's controller.";
    }
}

class JusticeEffect extends OneShotEffect {

    public JusticeEffect() {
        super(Outcome.Damage);
    }

    public JusticeEffect(final JusticeEffect effect) {
        super(effect);
    }

    @Override
    public JusticeEffect copy() {
        return new JusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        UUID targetId = this.targetPointer.getFirst(game, source);
        if (damageAmount != null && targetId != null) {
            Player player = game.getPlayer(targetId);
            if (player != null) {
                player.damage(damageAmount, targetId, source, game);
                return true;
            }
        }
        return false;
    }
}
