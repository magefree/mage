/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.effects.common.continuous;

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
 *
 * @author Plopman
 */
//20130711
/*
 * 903.11. If a commander would be put into its owner’s graveyard from anywhere, that player may put it into the command zone instead.
 * 903.12. If a commander would be put into the exile zone from anywhere, its owner may put it into the command zone instead.
 */
public class CommanderReplacementEffect extends ReplacementEffectImpl {

    private final UUID commanderId;
    private final boolean alsoHand;
    private final boolean alsoLibrary;

    public CommanderReplacementEffect(UUID commanderId, boolean alsoHand, boolean alsoLibrary) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a commander would be put into its owner’s graveyard from anywhere, that player may put it into the command zone instead. If a commander would be put into the exile zone from anywhere, its owner may put it into the command zone instead.";
        this.commanderId = commanderId;
        this.duration = Duration.EndOfGame;
        this.alsoHand = alsoHand;
        this.alsoLibrary = alsoLibrary;
    }

    public CommanderReplacementEffect(final CommanderReplacementEffect effect) {
        super(effect);
        this.commanderId = effect.commanderId;
        this.alsoHand = effect.alsoHand;
        this.alsoLibrary = effect.alsoLibrary;
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
        switch (((ZoneChangeEvent) event).getToZone()) {
            case HAND:
                if (!alsoHand && ((ZoneChangeEvent) event).getToZone() == Zone.HAND) {
                    return false;
                }
            case LIBRARY:
                if (!alsoLibrary && ((ZoneChangeEvent) event).getToZone() == Zone.LIBRARY) {
                    return false;
                }
            case GRAVEYARD:
            case EXILED:
                if (commanderId.equals(event.getTargetId())) {
                    return true;
                }
                break;
            case STACK:
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null) {
                    if (commanderId.equals(spell.getSourceId())) {
                        return true;
                    }
                }
                break;

        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = ((ZoneChangeEvent) event).getTarget();
            if (permanent != null) {
                Player player = game.getPlayer(permanent.getOwnerId());
                if (player != null && player.chooseUse(Outcome.Benefit, "Move commander to command zone?", source, game)) {
                    boolean result = permanent.moveToZone(Zone.COMMAND, source.getSourceId(), game, false);
                    if (!game.isSimulation()) {
                        game.informPlayers(player.getLogName() + " has moved his or her commander to the command zone");
                    }
                    return result;
                }
            }
        } else {
            Card card = null;
            if (((ZoneChangeEvent) event).getFromZone().equals(Zone.STACK)) {
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
                if (player != null && player.chooseUse(Outcome.Benefit, "Move commander to command zone?", source, game)) {
                    boolean result = card.moveToZone(Zone.COMMAND, source.getSourceId(), game, false);
                    if (!game.isSimulation()) {
                        game.informPlayers(player.getLogName() + " has moved his or her commander to the command zone");
                    }
                    return result;
                }
            }
        }
        return false;
    }
}
