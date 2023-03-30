package mage.abilities.effects.common.continuous;

import java.util.Locale;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * @author Plopman, JayDi85
 */
// 2019-07-12
/*
    903.9. If a commander would be exiled from anywhere or put into its owner’s hand, graveyard, or library from anywhere,
    its owner may put it into the command zone instead. This replacement effect may apply more than once to the same event.
    This is an exception to rule 614.5.
    903.9a If a commander is a melded permanent and its owner chooses to put it into the command zone this way,
    that permanent and the card representing it that isn’t a commander are put into the appropriate zone, and the card
    that represents it and is a commander is put into the command zone.

    The new state-based action that helps accomplish this rules change is worded as follows: (again, quoting from the announcement)
        “If a commander is in a graveyard or in exile and that card was put into that zone since the last time state-based actions were checked,
        its owner may put it into the command zone.

        If a commander would be put into its owner’s hand or library from anywhere, its owner may put it into the command zone instead.
        This replacement effect may apply more than once to the same event.”
 */
// Oathbreaker mode: If your Oathbreaker changes zones, you may return it to the Command Zone. The Signature Spell must return to the Command Zone.
public class CommanderReplacementEffect extends ReplacementEffectImpl {

    private final UUID commanderId;
    private final boolean alsoHand; // return from hand to command zone
    private final boolean alsoLibrary; // return from library to command zone
    private final boolean forceToMove;
    private final String commanderTypeName;

    /**
     *
     * @param commanderId
     * @param alsoHand          is the replacement effect also applied if
     *                          commander object goes to hand zone
     * @param alsoLibrary       is the replacement effect also applied if
     *                          commander object goes to library zone
     * @param forceToMove       used for signature spell of Oathbreaker format
     *                          (spell is mandatory moved to command zone
     *                          instead)
     * @param commanderTypeName type of commander object to set the correct text
     */
    public CommanderReplacementEffect(UUID commanderId, boolean alsoHand, boolean alsoLibrary, boolean forceToMove, String commanderTypeName) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        String mayStr = forceToMove ? " " : " may ";

        staticText = "If a " + commanderTypeName + " would be put into its owner's graveyard from anywhere, "
                + "that player" + mayStr + "put it into the command zone instead. "
                + "If a " + commanderTypeName + " would be put into the exile zone from anywhere, "
                + "its owner" + mayStr + "put it into the command zone instead.";
        this.commanderId = commanderId;
        this.duration = Duration.EndOfGame;
        this.alsoHand = alsoHand;
        this.alsoLibrary = alsoLibrary;
        this.forceToMove = forceToMove;
        this.commanderTypeName = commanderTypeName;
    }

    public CommanderReplacementEffect(final CommanderReplacementEffect effect) {
        super(effect);
        this.commanderId = effect.commanderId;
        this.alsoHand = effect.alsoHand;
        this.alsoLibrary = effect.alsoLibrary;
        this.forceToMove = effect.forceToMove;
        this.commanderTypeName = effect.commanderTypeName;
    }

    @Override
    public CommanderReplacementEffect copy() {
        return new CommanderReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (commanderId == null) {
            throw new IllegalArgumentException("commanderId has to be set");
        }
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!commanderId.equals(event.getTargetId())) {
            return false;
        }

        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;

        if (zEvent.getToZone().equals(Zone.HAND) && !alsoHand) {
            return false;
        }
        if (zEvent.getToZone().equals(Zone.LIBRARY) && !alsoLibrary) {
            return false;
        }

        // return to command zone
        switch (zEvent.getToZone()) {
            case LIBRARY:
            case HAND:
                return true;
        }
        if (forceToMove) {
            switch (zEvent.getToZone()) { // Normal commander movement is handled in state-based actions in GameImpl
                case BATTLEFIELD:
                case GRAVEYARD:
                case EXILED:
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        String originToZone = zEvent.getToZone().toString().toLowerCase(Locale.ENGLISH);

        if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null) {
                Player player = game.getPlayer(permanent.getOwnerId());
                if (player != null && (forceToMove || player.chooseUse(Outcome.Benefit, "Move " + permanent.getLogName() + " to command zone instead of your " + originToZone + "?", source, game))) {
                    zEvent.setToZone(Zone.COMMAND);
                    if (!game.isSimulation()) {
                        game.informPlayers(player.getLogName() + " has moved " + permanent.getLogName() + " to the command zone instead of their " + originToZone);
                    }
                }
            }
        } else {
            Card card = null;
            if (zEvent.getFromZone() == Zone.STACK) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null) {
                    card = game.getCard(spell.getSourceId());
                }
            }
            if (card == null) {
                card = game.getCard(event.getTargetId());
            }
            if (card != null) {
                Player player = game.getPlayer(card.getOwnerId());
                if (player != null && (forceToMove || player.chooseUse(Outcome.Benefit, "Move " + card.getLogName() + " to command zone instead of your " + originToZone + "?", source, game))) {
                    ((ZoneChangeEvent) event).setToZone(Zone.COMMAND);
                    if (!game.isSimulation()) {
                        game.informPlayers(player.getLogName() + " has moved " + card.getLogName() + " to the command zone instead of their " + originToZone);
                    }
                }
            }
        }
        return false;
    }
}
