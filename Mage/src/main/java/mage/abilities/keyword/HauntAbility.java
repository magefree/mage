package mage.abilities.keyword;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * 702.53. Haunt
 * <p>
 * 702.53a Haunt is a triggered ability. "Haunt" on a permanent means "When this
 * permanent is put into a graveyard from the battlefield, exile it haunting
 * target creature." "Haunt" on an instant or sorcery spell means "When this
 * spell is put into a graveyard during its resolution, exile it haunting target
 * creature."
 * <p>
 * 702.53b Cards that are in the exile zone as the result of a haunt ability
 * "haunt" the creature targeted by that ability. The phrase "creature it
 * haunts" refers to the object targeted by the haunt ability, regardless of
 * whether or not that object is still a creature.
 * <p>
 * 702.53c Triggered abilities of cards with haunt that refer to the haunted
 * creature can trigger in the exile zone.
 *
 * @author LevelX2
 */
public class HauntAbility extends TriggeredAbilityImpl {

    private boolean usedFromExile = false;

    public HauntAbility(Card card, Effect effect) {
        super(Zone.ALL, effect, false);
        boolean creatureHaunt = card.isCreature();
        addSubAbility(new HauntExileAbility(creatureHaunt));
        setTriggerPhrase((creatureHaunt ? "When {this} enters the battlefield or the creature it haunts dies, "
                                        : "When the creature {this} haunts dies, ")
        );
    }

    private HauntAbility(final HauntAbility ability) {
        super(ability);
        this.usedFromExile = ability.usedFromExile;
    }

    @Override
    public HauntAbility copy() {
        return new HauntAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return game.getState().getZone(getSourceId()) == Zone.BATTLEFIELD
                        && event.getTargetId().equals(getSourceId());
            case ZONE_CHANGE:
                if (usedFromExile
                        || game.getState().getZone(getSourceId()) != Zone.EXILED) {
                    return false;
                }
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (!zEvent.isDiesEvent()) {
                    return false;
                }
                Card card = game.getCard(getSourceId());
                if (card == null
                        || game.getCard(event.getTargetId()) == null) {
                    return false;
                }
                String key = new StringBuilder("Haunting_")
                        .append(getSourceId().toString())
                        .append(card.getZoneChangeCounter(game)
                                + (game.getPermanentOrLKIBattlefield(event.getTargetId()))
                                .getZoneChangeCounter(game)).toString();
                Object object = game.getState().getValue(key);
                if (!(object instanceof FixedTarget)) {
                    return false;
                }
                FixedTarget target = (FixedTarget) object;
                if (target.getTarget() != null
                        && target.getTarget()
                        .equals(event.getTargetId())) {
                    usedFromExile = true;
                    return true;
                }
        }
        return false;
    }
}

class HauntExileAbility extends ZoneChangeTriggeredAbility {

    private static final String RULE_TEXT_CREATURE = "Haunt <i>(When this creature dies, "
            + "exile it haunting target creature.)</i>";
    private static final String RULE_TEXT_SPELL = "Haunt <i>(When this spell card is put "
            + "into a graveyard after resolving, exile it haunting target creature.)</i>";

    private boolean creatureHaunt;

    // TODO: It's not checked yet, if the Haunt spell has resolved (and was not countered or removed from stack).
    public HauntExileAbility(boolean creatureHaunt) {
        super(creatureHaunt ? Zone.BATTLEFIELD : Zone.STACK, Zone.GRAVEYARD, new HauntEffect(), null, false);
        this.creatureHaunt = creatureHaunt;
        this.setRuleAtTheTop(creatureHaunt);
        this.addTarget(new TargetCreaturePermanent());
    }

    private HauntExileAbility(final HauntExileAbility ability) {
        super(ability);
        this.creatureHaunt = ability.creatureHaunt;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        boolean fromOK = true;
        Permanent sourcePermanent = (Permanent) game.getLastKnownInformation(sourceId, Zone.BATTLEFIELD);
        if (creatureHaunt
                && sourcePermanent == null) {
            // check it was previously on battlefield
            fromOK = false;
        }
        if (!this.hasSourceObjectAbility(game, sourcePermanent, event)) {
            return false;
        }
        // check now it is in graveyard
        Zone after = game.getState().getZone(sourceId);
        return fromOK
                && after != null
                && Zone.GRAVEYARD.match(after);
    }

    @Override
    public String getRule() {
        return creatureHaunt ? RULE_TEXT_CREATURE : RULE_TEXT_SPELL;
    }

    @Override
    public HauntExileAbility copy() {
        return new HauntExileAbility(this);
    }
}

class HauntEffect extends OneShotEffect {

    HauntEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it haunting target creature";
    }

    private HauntEffect(final HauntEffect effect) {
        super(effect);
    }

    @Override
    public HauntEffect copy() {
        return new HauntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }
        Permanent hauntedCreature = game.getPermanent(targetPointer.getFirst(game, source));
        // haunting card will only be moved to exile, if
        if (hauntedCreature == null) {
            return false;
        }
        if (!player.moveCards(card, Zone.EXILED, source, game)) {
            return true;
        }
        // remember the haunted creature
        String key = new StringBuilder("Haunting_")
                .append(source.getSourceId().toString())
                .append(card.getZoneChangeCounter(game)
                        + hauntedCreature.getZoneChangeCounter(game)).toString();  // in case it is blinked
        game.getState().setValue(key, new FixedTarget(targetPointer.getFirst(game, source), game));
        card.addInfo("hauntinfo", new StringBuilder("Haunting ").append(hauntedCreature.getLogName()).toString(), game);
        hauntedCreature.addInfo("hauntinfo", new StringBuilder("Haunted by ").append(card.getLogName()).toString(), game);
        game.informPlayers(new StringBuilder(card.getName()).append(" haunting ").append(hauntedCreature.getLogName()).toString());
        return true;
    }
}
