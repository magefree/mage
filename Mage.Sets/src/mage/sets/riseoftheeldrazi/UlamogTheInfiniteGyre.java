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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PutIntoGraveFromAnywhereTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class UlamogTheInfiniteGyre extends CardImpl<UlamogTheInfiniteGyre> {

    public UlamogTheInfiniteGyre(UUID ownerId) {
        super(ownerId, 12, "Ulamog, the Infinite Gyre", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{11}");
        this.expansionSetCode = "ROE";
        this.supertype.add("Legendary");
        this.subtype.add("Eldrazi");

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);
        
        // When you cast Ulamog, the Infinite Gyre, destroy target permanent.
        this.addAbility(new UlamogTheInfiniteGyreDestroyOnCastAbility());
        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        this.addAbility(new AnnihilatorAbility(4));
        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // When Ulamog is put into a graveyard from anywhere, its owner shuffles his or her graveyard into his or her library.
        this.addAbility(new PutIntoGraveFromAnywhereTriggeredAbility(new UlamogTheInfiniteGyreEnterGraveyardEffect(), false));
    }

    public UlamogTheInfiniteGyre(final UlamogTheInfiniteGyre card) {
        super(card);
    }

    @Override
    public UlamogTheInfiniteGyre copy() {
        return new UlamogTheInfiniteGyre(this);
    }
}

class UlamogTheInfiniteGyreDestroyOnCastAbility extends TriggeredAbilityImpl<UlamogTheInfiniteGyreDestroyOnCastAbility> {

    UlamogTheInfiniteGyreDestroyOnCastAbility ( ) {
        super(Zone.STACK, new DestroyTargetEffect());
        this.addTarget(new TargetPermanent(true));
    }

    UlamogTheInfiniteGyreDestroyOnCastAbility(UlamogTheInfiniteGyreDestroyOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ( event.getType() == EventType.SPELL_CAST ) {
            Spell spell = (Spell)game.getObject(event.getTargetId());
            if ( this.getSourceId().equals(spell.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public UlamogTheInfiniteGyreDestroyOnCastAbility copy() {
        return new UlamogTheInfiniteGyreDestroyOnCastAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder("When you cast {this}, ").append(super.getRule()).toString();
    }
}

class UlamogTheInfiniteGyreEnterGraveyardEffect extends OneShotEffect<UlamogTheInfiniteGyreEnterGraveyardEffect> {

    UlamogTheInfiniteGyreEnterGraveyardEffect ( ) {
        super(Outcome.Benefit);
        staticText = "its owner shuffles his or her graveyard into his or her library";
    }

    UlamogTheInfiniteGyreEnterGraveyardEffect(UlamogTheInfiniteGyreEnterGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID ownerId = null;
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            ownerId = card.getOwnerId();
        }
        if (ownerId == null) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            if (permanent != null) {
                ownerId = permanent.getOwnerId();
            }
        }
        Player player = game.getPlayer(ownerId);
        if (player != null) {
            player.getLibrary().addAll(player.getGraveyard().getCards(game), game);
            player.getGraveyard().clear();
            player.shuffleLibrary(game);
            return true;
        }
        return false;
    }

    @Override
    public UlamogTheInfiniteGyreEnterGraveyardEffect copy() {
        return new UlamogTheInfiniteGyreEnterGraveyardEffect(this);
    }
}
