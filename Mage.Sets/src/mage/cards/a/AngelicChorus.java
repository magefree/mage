package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class AngelicChorus extends CardImpl {

    public AngelicChorus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // Whenever a creature enters the battlefield under your control, you gain life equal to its toughness.
        this.addAbility(new AngelicChorusTriggeredAbility());
    }

    private AngelicChorus(final AngelicChorus card) {
        super(card);
    }

    @Override
    public AngelicChorus copy() {
        return new AngelicChorus(this);
    }
}

class AngelicChorusTriggeredAbility extends TriggeredAbilityImpl {

    public AngelicChorusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AngelicChorusEffect(), false);
    }

    private AngelicChorusTriggeredAbility(final AngelicChorusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(this.controllerId)) {
            this.getEffects().get(0).setValue("lifeSource", event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under your control, you gain life equal to its toughness.";
    }

    @Override
    public AngelicChorusTriggeredAbility copy() {
        return new AngelicChorusTriggeredAbility(this);
    }
}

class AngelicChorusEffect extends OneShotEffect {

    public AngelicChorusEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to its toughness";
    }

    private AngelicChorusEffect(final AngelicChorusEffect effect) {
        super(effect);
    }

    @Override
    public AngelicChorusEffect copy() {
        return new AngelicChorusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("lifeSource");
        Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
        if (creature != null) {
            int amount = creature.getToughness().getValue();
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }
}