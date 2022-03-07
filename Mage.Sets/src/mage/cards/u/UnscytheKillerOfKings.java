package mage.cards.u;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class UnscytheKillerOfKings extends CardImpl {

    public UnscytheKillerOfKings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}{B}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+3 and has first strike.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has first strike"));

        // Whenever a creature dealt damage by equipped creature this turn dies, you may exile that card. If you do, create a 2/2 black Zombie creature token.
        this.addAbility(new UnscytheKillerOfKingsTriggeredAbility(new UnscytheEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private UnscytheKillerOfKings(final UnscytheKillerOfKings card) {
        super(card);
    }

    @Override
    public UnscytheKillerOfKings copy() {
        return new UnscytheKillerOfKings(this);
    }
}

class UnscytheKillerOfKingsTriggeredAbility extends TriggeredAbilityImpl {

    public UnscytheKillerOfKingsTriggeredAbility(Effect effect) {
        super(Zone.ALL, effect, true);
    }

    public UnscytheKillerOfKingsTriggeredAbility(final UnscytheKillerOfKingsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnscytheKillerOfKingsTriggeredAbility copy() {
        return new UnscytheKillerOfKingsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!((ZoneChangeEvent) event).isDiesEvent()) {
            return false;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.getTarget().isCreature(game)) {
            return false;
        } // target token can't create Zombie
        Permanent equipment = game.getPermanent(getSourceId());
        // the currently equiped creature must have done damage to the dying creature
        if (equipment == null || equipment.getAttachedTo() == null) {
            return false;
        }
        boolean damageDealt = false;
        for (MageObjectReference mor : zEvent.getTarget().getDealtDamageByThisTurn()) {
            if (mor.refersTo(equipment.getAttachedTo(), game)) {
                getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature dealt damage by equipped creature this turn dies, ";
    }
}

class UnscytheEffect extends OneShotEffect {

    public UnscytheEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may exile that card. If you do, create a 2/2 black Zombie creature token";
    }

    public UnscytheEffect(final UnscytheEffect effect) {
        super(effect);
    }

    @Override
    public UnscytheEffect copy() {
        return new UnscytheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD && controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.GRAVEYARD, true)) {
            ZombieToken zombie = new ZombieToken();
            return zombie.putOntoBattlefield(1, game, source, source.getControllerId());
        }
        return true;
    }
}
