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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class MaelstromNexus extends CardImpl<MaelstromNexus> {

    public MaelstromNexus(UUID ownerId) {
        super(ownerId, 130, "Maelstrom Nexus", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);

        // The first spell you cast each turn has cascade.
        this.addAbility(new MaelstromNexusTriggeredAbility());
        this.addWatcher(new FirstSpellCastThisTurnWatcher());

    }

    public MaelstromNexus(final MaelstromNexus card) {
        super(card);
    }

    @Override
    public MaelstromNexus copy() {
        return new MaelstromNexus(this);
    }
}

class MaelstromNexusTriggeredAbility extends TriggeredAbilityImpl<MaelstromNexusTriggeredAbility> {

    public MaelstromNexusTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CascadeEffect());
    }

    public MaelstromNexusTriggeredAbility(MaelstromNexusTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            System.out.println("A spell was cast");
            Spell spell = game.getStack().getSpell(event.getTargetId());
            FirstSpellCastThisTurnWatcher watcher = (FirstSpellCastThisTurnWatcher) game.getState().getWatchers().get("FirstSpellCastThisTurn", this.getSourceId());
            if (spell != null
                    && watcher != null
                    && watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MaelstromNexusTriggeredAbility copy() {
        return new MaelstromNexusTriggeredAbility(this);
    }
}

class FirstSpellCastThisTurnWatcher extends WatcherImpl<FirstSpellCastThisTurnWatcher> {

    int spellCount = 0;

    public FirstSpellCastThisTurnWatcher() {
        super("FirstSpellCastThisTurn", WatcherScope.CARD);
    }

    public FirstSpellCastThisTurnWatcher(final FirstSpellCastThisTurnWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId() == controllerId) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell != null) {
                spellCount++;
                if (spellCount == 1) {
                    condition = true;
                } else {
                    condition = false;
                }
            }
        }
    }

    @Override
    public FirstSpellCastThisTurnWatcher copy() {
        return new FirstSpellCastThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        spellCount = 0;
    }
}

class CascadeEffect extends OneShotEffect<CascadeEffect> {

    public CascadeEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "The first spell you cast each turn has cascade";
    }

    public CascadeEffect(CascadeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        System.out.println("Inside the CascadeEffect method");
        Card card;
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exile = game.getExile().createZone(source.getSourceId(), player.getName() + " Cascade");
        int sourceCost = game.getCard(source.getSourceId()).getManaCost().convertedManaCost();
        do {
            card = player.getLibrary().removeFromTop(game);
            if (card == null) {
                break;
            }
                
            card.moveToExile(exile.getId(), exile.getName(), source.getId(), game);
        } while (card.getCardType().contains(CardType.LAND) || card.getManaCost().convertedManaCost() >= sourceCost);

        if (card != null) {
            if (player.chooseUse(outcome, "Use cascade effect on " + card.getName() + "?", game)) {
                player.cast(card.getSpellAbility(), game, true);
                exile.remove(card.getId());
            }
        }

        while (exile.size() > 0) {
            card = exile.getRandom(game);
            exile.remove(card.getId());
            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
        }

        return true;
    }

    @Override
    public CascadeEffect copy() {
        return new CascadeEffect(this);
    }

}
