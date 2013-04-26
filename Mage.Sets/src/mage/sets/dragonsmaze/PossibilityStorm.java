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
package mage.sets.dragonsmaze;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */


public class PossibilityStorm extends CardImpl<PossibilityStorm> {

    public PossibilityStorm(UUID ownerId) {
        super(ownerId, 34, "Possibility Storm", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");
        this.expansionSetCode = "DGM";

        this.color.setRed(true);

        // Whenever a player casts a spell from his or her hand, that player exiles it, then exiles cards from
        // the top of his or her library until he or she exiles a card that shares a card type with it. That
        // player may cast that card without paying its mana cost. Then he or she puts all cards exiled with
        // Possibility Storm on the bottom of his or her library in a random order.
        this.addAbility(new PossibilityStormTriggeredAbility());
    }

    public PossibilityStorm(final PossibilityStorm card) {
        super(card);
    }

    @Override
    public PossibilityStorm copy() {
        return new PossibilityStorm(this);
    }
}


class PossibilityStormTriggeredAbility extends TriggeredAbilityImpl<PossibilityStormTriggeredAbility> {

    public PossibilityStormTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PossibilityStormEffect(), false);
    }

    public PossibilityStormTriggeredAbility(final PossibilityStormTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PossibilityStormTriggeredAbility copy() {
        return new PossibilityStormTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.SPELL_CAST && event.getZone() == Zone.HAND) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell from his or her hand, " + super.getRule();
    }
}

class PossibilityStormEffect extends OneShotEffect<PossibilityStormEffect> {

    public PossibilityStormEffect() {
        super(Outcome.Neutral);
        staticText = "that player exiles it, then exiles cards from the top of his or her library until he or she exiles a card that shares a card type with it. That player may cast that card without paying its mana cost. Then he or she puts all cards exiled with Possibility Storm on the bottom of his or her library in a random order";
    }

    public PossibilityStormEffect(final PossibilityStormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            if (spell.moveToExile(source.getSourceId(), "Possibility Storm Exile", source.getSourceId(), game)) {
                Player player = game.getPlayer(spell.getControllerId());
                if (player != null && player.getLibrary().size() > 0) {
                    Library library = player.getLibrary();
                    Card card;
                    do {
                        card = library.removeFromTop(game);
                        if (card != null) {
                            card.moveToExile(source.getSourceId(), "Possibility Storm Exile", source.getSourceId(), game);
                        }
                    } while (library.size() > 0 && card != null && !sharesType(card, spell.getCardType()));

                    if (card != null && sharesType(card, spell.getCardType())) {
                        if(player.chooseUse(Outcome.PlayForFree, new StringBuilder("Cast ").append(card.getName()).append(" without paying cost?").toString(), game)) {
                            player.cast(card.getSpellAbility(), game, true);
                        }
                    }

                    ExileZone exile = game.getExile().getExileZone(source.getSourceId());
                    if (exile != null) {
                        while (exile.size() > 0) {
                            card = exile.getRandom(game);
                            exile.remove(card.getId());
                            card.moveToZone(Zone.LIBRARY, source.getId(), game, false);
                        }
                    }

                }
                return true;
            }
        }
        return false;
    }

    private boolean sharesType (Card card, List<CardType> cardTypes) {
        for (Constants.CardType type : card.getCardType()) {
            if (cardTypes.contains(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PossibilityStormEffect copy() {
        return new PossibilityStormEffect(this);
    }

}
