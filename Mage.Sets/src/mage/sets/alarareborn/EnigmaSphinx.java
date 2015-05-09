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
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class EnigmaSphinx extends CardImpl {

    public EnigmaSphinx(UUID ownerId) {
        super(ownerId, 106, "Enigma Sphinx", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{W}{U}{B}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Sphinx");

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Enigma Sphinx is put into your graveyard from the battlefield, put it into your library third from the top.        
        this.addAbility(new EnigmaSphinxTriggeredAbility(new EnigmaSphinxEffect()));

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    public EnigmaSphinx(final EnigmaSphinx card) {
        super(card);
    }

    @Override
    public EnigmaSphinx copy() {
        return new EnigmaSphinx(this);
    }
}

class EnigmaSphinxTriggeredAbility extends TriggeredAbilityImpl {

    public EnigmaSphinxTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public EnigmaSphinxTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, optional);
    }

    EnigmaSphinxTriggeredAbility(EnigmaSphinxTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EnigmaSphinxTriggeredAbility copy() {
        return new EnigmaSphinxTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent permanent = zEvent.getTarget();
            if (permanent != null &&
                    zEvent.getToZone() == Zone.GRAVEYARD &&
                    zEvent.getFromZone() == Zone.BATTLEFIELD &&
                    permanent.getId().equals(this.getSourceId()) &&
                    // 5/1/2009 If you control an Enigma Sphinx that's owned by another player, it's put into that player's 
                    //          graveyard from the battlefield, so Enigma Sphinx's middle ability won't trigger.                    
                    permanent.getOwnerId().equals(permanent.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} is put into your graveyard from the battlefield, " + super.getRule();
    }
}

class EnigmaSphinxEffect extends OneShotEffect {

    public EnigmaSphinxEffect() {
        super(Outcome.ReturnToHand);
        staticText = "put it into your library third from the top";
    }

    public EnigmaSphinxEffect(final EnigmaSphinxEffect effect) {
        super(effect);
    }

    @Override
    public EnigmaSphinxEffect copy() {
        return new EnigmaSphinxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null && card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true)) {
                // Move Sphinx to third position
                game.informPlayers(card.getLogName() + " is put into " + owner.getLogName() +"'s library third from the top");
                Library lib = owner.getLibrary();
                if (lib != null) {
                    Card card1 = lib.removeFromTop(game);
                    if (card1 != null && card1.getId().equals(source.getSourceId())) {
                        Card card2 = lib.removeFromTop(game);
                        Card card3 = lib.removeFromTop(game);
                        lib.putOnTop(card1, game);
                        if (card3 != null) {
                            lib.putOnTop(card3, game);
                        }
                        if (card2 != null) {
                            lib.putOnTop(card2, game);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
