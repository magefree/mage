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
package mage.sets.scourge;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class DecreeOfAnnihilation extends CardImpl {

    public DecreeOfAnnihilation(UUID ownerId) {
        super(ownerId, 85, "Decree of Annihilation", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{8}{R}{R}");
        this.expansionSetCode = "SCG";

        // Exile all artifacts, creatures, and lands from the battlefield, all cards from all graveyards, and all cards from all hands.
        this.getSpellAbility().addEffect(new DecreeOfAnnihilationEffect());

        // Cycling {5}{R}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{5}{R}{R}")));
        
        // When you cycle Decree of Annihilation, destroy all lands.
        Ability ability = new CycleTriggeredAbility(new DestroyAllEffect(new FilterLandPermanent("lands")), false);
        this.addAbility(ability);
    }

    public DecreeOfAnnihilation(final DecreeOfAnnihilation card) {
        super(card);
    }

    @Override
    public DecreeOfAnnihilation copy() {
        return new DecreeOfAnnihilation(this);
    }
}

class DecreeOfAnnihilationEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterPermanent("");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }
    
    public DecreeOfAnnihilationEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all artifacts, creatures, and lands from the battlefield, all cards from all graveyards, and all cards from all hands";
    }

    public DecreeOfAnnihilationEffect(final DecreeOfAnnihilationEffect effect) {
        super(effect);
    }

    @Override
    public DecreeOfAnnihilationEffect copy() {
        return new DecreeOfAnnihilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.moveToExile(id, "all artifacts, creatures, and land", id, game);
        }
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getHand().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source.getSourceId(), game);
                    }
                }
                for (UUID cid : player.getGraveyard().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source.getSourceId(), game);
                    }
                }
            }
        }
        return true;
    }
}
