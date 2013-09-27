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
package mage.sets.commander;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author cbt33, plopman (Entomb)
 */
public class BuriedAlive extends CardImpl<BuriedAlive> {

    
    public BuriedAlive(UUID ownerId) {
        super(ownerId, 74, "Buried Alive", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}");
        this.expansionSetCode = "CMD";

        this.color.setBlack(true);
        this.getSpellAbility().addEffect(new BuriedAliveEffect());
        // Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library.
        
    }

    public BuriedAlive(final BuriedAlive card) {
        super(card);
    }

    @Override
    public BuriedAlive copy() {
        return new BuriedAlive(this);
    }
}

class BuriedAliveEffect extends SearchEffect<BuriedAliveEffect> {

  public BuriedAliveEffect() {
        super(new TargetCardInLibrary(1, 3, new FilterCreatureCard()), Outcome.Neutral);
        staticText = "Search your library for up to three creature cards and put them into your graveyard. Then shuffle your library";
    }

    public BuriedAliveEffect(final BuriedAliveEffect effect) {
        super(effect);
    }

    @Override
    public BuriedAliveEffect copy() {
        return new BuriedAliveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Cards cards = new CardsImpl();
                for (UUID cardId: (List<UUID>)target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null){
                        card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                    }
                }
            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
        return false;
    }

    
}
