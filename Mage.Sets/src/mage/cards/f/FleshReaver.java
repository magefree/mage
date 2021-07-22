package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FleshReaver extends CardImpl {

    public FleshReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Flesh Reaver deals damage to a creature or opponent, Flesh Reaver deals that much damage to you.
        this.addAbility(new FleshReaverTriggeredAbility());
    }

    private FleshReaver(final FleshReaver card) {
        super(card);
    }

    @Override
    public FleshReaver copy() {
        return new FleshReaver(this);
    }
}

class FleshReaverTriggeredAbility extends TriggeredAbilityImpl {

    FleshReaverTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FleshReaverEffect());
    }

    private FleshReaverTriggeredAbility(final FleshReaverTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public FleshReaverTriggeredAbility copy() {
        return new FleshReaverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getSourceId().equals(getSourceId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if ((permanent != null && permanent.isCreature(game))
                || game.getOpponents(event.getTargetId()).contains(getControllerId())) {
            this.getEffects().setValue("damage", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to a creature or opponent, {this} deals that much damage to you.";
    }

}

class FleshReaverEffect extends OneShotEffect {

    FleshReaverEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals that much damage to you.";
    }

    private FleshReaverEffect(final FleshReaverEffect effect) {
        super(effect);
    }

    @Override
    public FleshReaverEffect copy() {
        return new FleshReaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (creature == null || controller == null) {
            return false;
        }
        int damageToDeal = (Integer) getValue("damage");
        if (damageToDeal > 0) {
            controller.damage(damageToDeal, source.getSourceId(), source, game);
        }
        return true;
    }
}
