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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamageCreatureEvent;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public class GloomSurgeon extends CardImpl<GloomSurgeon> {

    public GloomSurgeon(UUID ownerId) {
        super(ownerId, 104, "Gloom Surgeon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Spirit");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If combat damage would be dealt to Gloom Surgeon, prevent that damage and exile that many cards from the top of your library.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GloomSurgeonEffect()));
    }

    public GloomSurgeon(final GloomSurgeon card) {
        super(card);
    }

    @Override
    public GloomSurgeon copy() {
        return new GloomSurgeon(this);
    }
}

class GloomSurgeonEffect extends ReplacementEffectImpl<GloomSurgeonEffect> {

    GloomSurgeonEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Exile);
        staticText = "If combat damage would be dealt to {this}, prevent that damage and exile that many cards from the top of your library";
    }

    GloomSurgeonEffect(final GloomSurgeonEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int preventedDamage = event.getAmount();
            event.setAmount(0);

            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                int cardsCount = Math.min(preventedDamage, player.getLibrary().size());
                for (int i = 0; i < cardsCount; i++) {
                    Card card = player.getLibrary().removeFromTop(game);
                    if (card != null) {
                        card.moveToExile(source.getId(), "Gloom Surgeon", source.getSourceId(), game);
                    } else {
                        break;
                    }
                }
            }

            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), preventedDamage));
            return true;
        }

        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_CREATURE && event.getTargetId().equals(source.getSourceId())) {
            DamageCreatureEvent damageEvent = (DamageCreatureEvent) event;
            if (damageEvent.isCombatDamage()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GloomSurgeonEffect copy() {
        return new GloomSurgeonEffect(this);
    }
}
