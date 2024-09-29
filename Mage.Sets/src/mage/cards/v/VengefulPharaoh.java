
package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.*;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author North
 */
public final class VengefulPharaoh extends CardImpl {

    public VengefulPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Deathtouch (Any amount of damage this deals to a creature is enough to destroy it.)
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever combat damage is dealt to you or a planeswalker you control, if Vengeful Pharaoh is in your graveyard, destroy target attacking creature, then put Vengeful Pharaoh on top of your library.
        this.addAbility(new VengefulPharaohTriggeredAbility());
    }

    private VengefulPharaoh(final VengefulPharaoh card) {
        super(card);
    }

    @Override
    public VengefulPharaoh copy() {
        return new VengefulPharaoh(this);
    }
}

class VengefulPharaohTriggeredAbility extends TriggeredAbilityImpl {

    public VengefulPharaohTriggeredAbility() {
        super(Zone.GRAVEYARD, new VengefulPharaohEffect(), false);
        this.addTarget(new TargetAttackingCreature());
    }

    private VengefulPharaohTriggeredAbility(final VengefulPharaohTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VengefulPharaohTriggeredAbility copy() {
        return new VengefulPharaohTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        // Vengeful Pharaoh must be in your graveyard when combat damage is dealt to you or a planeswalker you control
        // in order for its ability to trigger. That is, it can’t die and trigger from your graveyard during the same
        // combat damage step. (2011-09-22)

        // If Vengeful Pharaoh is no longer in your graveyard when the triggered ability would resolve, the triggered
        // ability won’t do anything. (2011-09-22)
        return game.getState().getZone(getSourceId()) == Zone.GRAVEYARD;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        // If multiple creatures deal combat damage to you simultaneously, Vengeful Pharaoh will only trigger once.
        // (2011-09-22)
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {

        if ((event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PLAYER
                && event.getTargetId().equals(this.getControllerId()))) {
            DamagedBatchForOnePlayerEvent dEvent = (DamagedBatchForOnePlayerEvent) event;
            return dEvent.isCombatDamage() && dEvent.getAmount() > 0;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            DamagedBatchForOnePermanentEvent dEvent = (DamagedBatchForOnePermanentEvent) event;
            return permanent != null
                    && permanent.isPlaneswalker(game)
                    && permanent.isControlledBy(this.getControllerId())
                    && dEvent.isCombatDamage() && dEvent.getAmount() > 0;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever combat damage is dealt to you or a planeswalker you control, if {this} is in your " +
                "graveyard, destroy target attacking creature, then put {this} on top of your library.";
    }
}

class VengefulPharaohEffect extends OneShotEffect {

    VengefulPharaohEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy target attacking creature, then put {this} on top of your library";
    }

    private VengefulPharaohEffect(final VengefulPharaohEffect effect) {
        super(effect);
    }

    @Override
    public VengefulPharaohEffect copy() {
        return new VengefulPharaohEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (card != null && controller != null) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent == null) {
                // If the attacking creature is an illegal target when the triggered ability tries to resolve,
                // it won’t resolve and none of its effects will happen. Vengeful Pharaoh will remain in your graveyard.
                // (2011-09-22)
                return false;
            }
            permanent.destroy(source, game, false);
            controller.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            return true;
        }
        return false;
    }
}
