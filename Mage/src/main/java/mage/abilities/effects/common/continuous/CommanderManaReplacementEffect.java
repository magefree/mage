
package mage.abilities.effects.common.continuous;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;

/**
 * @author LevelX
 */
//20130711
/*
 * 903.9. If mana would be added to a player's mana pool of a color that isn't in the color identity
 * of that player's commander, that amount of colorless mana is added to that player's mana pool instead.
 *
 * Commander rule #4 was removed Jan. 18, 2016
 *
 */
@Deprecated
public class CommanderManaReplacementEffect extends ReplacementEffectImpl {

    private final UUID playerId;
    private final FilterMana commanderMana;

    public CommanderManaReplacementEffect(UUID playerId, FilterMana commanderMana) {
        super(Duration.EndOfGame, Outcome.Neutral);
        staticText = "If mana would be added to a player's mana pool of a color that isn't in the color identity of that player's commander, that amount of colorless mana is added to that player's mana pool instead.";
        this.playerId = playerId;
        this.commanderMana = commanderMana;
    }

    protected CommanderManaReplacementEffect(final CommanderManaReplacementEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.commanderMana = effect.commanderMana;
    }

    @Override
    public CommanderManaReplacementEffect copy() {
        return new CommanderManaReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Mana mana = ((ManaEvent) event).getMana();
        if (mana.getBlack() > 0 && !commanderMana.isBlack()) {
            for (int i = 0; i < mana.getBlack(); i++) {
                mana.increaseGeneric();
            }
            mana.setBlack(0);
        }
        if (mana.getBlue() > 0 && !commanderMana.isBlue()) {
            for (int i = 0; i < mana.getBlue(); i++) {
                mana.increaseGeneric();
            }
            mana.setBlue(0);
        }
        if (mana.getGreen() > 0 && !commanderMana.isGreen()) {
            for (int i = 0; i < mana.getGreen(); i++) {
                mana.increaseGeneric();
            }
            mana.setGreen(0);
        }
        if (mana.getRed() > 0 && !commanderMana.isRed()) {
            for (int i = 0; i < mana.getRed(); i++) {
                mana.increaseGeneric();
            }
            mana.setRed(0);
        }
        if (mana.getWhite() > 0 && !commanderMana.isWhite()) {
            for (int i = 0; i < mana.getWhite(); i++) {
                mana.increaseGeneric();
            }
            mana.setWhite(0);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(playerId);
    }

}
