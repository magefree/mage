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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.SetPlayerLifeAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SwayOfTheStars extends CardImpl {

    public SwayOfTheStars(UUID ownerId) {
        super(ownerId, 54, "Sway of the Stars", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{8}{U}{U}");
        this.expansionSetCode = "BOK";

        // Each player shuffles his or her hand, graveyard, and permanents he or she owns into his or her library, then draws seven cards. Each player's life total becomes 7.
        this.getSpellAbility().addEffect(new SwayOfTheStarsEffect());
        Effect effect = new DrawCardAllEffect(7);
        effect.setText(", then draws seven cards");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new SetPlayerLifeAllEffect(7));

    }

    public SwayOfTheStars(final SwayOfTheStars card) {
        super(card);
    }

    @Override
    public SwayOfTheStars copy() {
        return new SwayOfTheStars(this);
    }
}

class SwayOfTheStarsEffect extends OneShotEffect {

    public SwayOfTheStarsEffect() {
        super(Outcome.Neutral);
        staticText = "Each player shuffles his or her hand, graveyard, and permanents he or she owns into his or her library, then draws seven cards. Each player's life total becomes 7";
    }

    public SwayOfTheStarsEffect(final SwayOfTheStarsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getHand(), Zone.HAND, Zone.LIBRARY, source, game);
                player.moveCards(player.getGraveyard(), Zone.GRAVEYARD, Zone.LIBRARY, source, game);
                FilterPermanent filter = new FilterPermanent();
                filter.add(new OwnerIdPredicate(playerId));
                for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controller.getId(), source.getSourceId(), game)) {
                    permanent.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                }
                player.shuffleLibrary(game);
            }
        }
        return true;
    }

    @Override
    public SwayOfTheStarsEffect copy() {
        return new SwayOfTheStarsEffect(this);
    }

}
