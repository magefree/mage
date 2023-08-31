
package mage.cards.v;

import mage.MageInt;
import mage.MageObjectReference;
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
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.Step;
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

    Step stepTriggeredPlayer;
    int turnTriggeredPlayer;

    Step stepTriggeredPlansewalker;
    int turnTriggeredPlaneswalker;

    public VengefulPharaohTriggeredAbility() {
        super(Zone.GRAVEYARD, new VengefulPharaohEffect(), false);
        this.addTarget(new TargetAttackingCreature());
    }

    private VengefulPharaohTriggeredAbility(final VengefulPharaohTriggeredAbility ability) {
        super(ability);
        this.stepTriggeredPlansewalker = ability.stepTriggeredPlansewalker;
        this.turnTriggeredPlaneswalker = ability.turnTriggeredPlaneswalker;
        this.stepTriggeredPlayer = ability.stepTriggeredPlayer;
        this.turnTriggeredPlayer = ability.turnTriggeredPlayer;
    }

    @Override
    public VengefulPharaohTriggeredAbility copy() {
        return new VengefulPharaohTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        // 9/22/2011 - If multiple creatures deal combat damage to you and to a planeswalker you control 
        // simultaneously, Vengeful Pharaoh will trigger twice. The first trigger will cause Vengeful Pharaoh 
        // to be put on top of your library. The second trigger will then do nothing, as Vengeful Pharaoh is 
        // no longer in your graveyard when it tries to resolve. Note that the second trigger will do nothing 
        // even if Vengeful Pharaoh is put back into your graveyard before it tries to resolve, as it's a 
        // different Vengeful Pharaoh than the one that was there before.
        MageObjectReference mor = new MageObjectReference(getSourceId(), game);
        return mor.refersTo(this.getSourceObject(game), game);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.DAMAGED_PLAYER && event.getTargetId().equals(this.getControllerId()))
                && ((DamagedEvent) event).isCombatDamage()) {
            if (!game.getPhase().getStep().equals(stepTriggeredPlayer) || game.getTurnNum() != turnTriggeredPlayer) {
                stepTriggeredPlayer = game.getPhase().getStep();
                turnTriggeredPlayer = game.getTurnNum();
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT && ((DamagedEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isPlaneswalker(game) && permanent.isControlledBy(this.getControllerId())) {
                if (!game.getPhase().getStep().equals(stepTriggeredPlansewalker) || game.getTurnNum() != turnTriggeredPlaneswalker) {
                    stepTriggeredPlansewalker = game.getPhase().getStep();
                    turnTriggeredPlaneswalker = game.getTurnNum();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever combat damage is dealt to you or a planeswalker you control, if {this} is in your graveyard, destroy target attacking creature, then put {this} on top of your library.";
    }
}

class VengefulPharaohEffect extends OneShotEffect {

    public VengefulPharaohEffect() {
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
            if (permanent != null) {
                permanent.destroy(source, game, false);
            }
            controller.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            return true;
        }
        return false;
    }
}
