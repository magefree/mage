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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LoneFox

 */
public class CrumblingSanctuary extends CardImpl {

    public CrumblingSanctuary(UUID ownerId) {
        super(ownerId, 292, "Crumbling Sanctuary", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "MMQ";

        // If damage would be dealt to a player, that player exiles that many cards from the top of his or her library instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CrumblingSanctuaryEffect()));
    }

    public CrumblingSanctuary(final CrumblingSanctuary card) {
        super(card);
    }

    @Override
    public CrumblingSanctuary copy() {
        return new CrumblingSanctuary(this);
    }
}

class CrumblingSanctuaryEffect extends PreventionEffectImpl {

    public CrumblingSanctuaryEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If damage would be dealt to a player, that player exiles that many cards from the top of his or her library instead.";
    }

    public CrumblingSanctuaryEffect(final CrumblingSanctuaryEffect effect) {
        super(effect);
    }

    @Override
    public CrumblingSanctuaryEffect copy() {
        return new CrumblingSanctuaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = event.getAmount();
        Player player = game.getPlayer(event.getTargetId());
        if(player != null) {
            preventDamageAction(event, source, game);
            player.moveCards(player.getLibrary().getTopCards(game, amount), Zone.LIBRARY, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && (game.getPlayer(event.getTargetId()) != null);
    }

}
