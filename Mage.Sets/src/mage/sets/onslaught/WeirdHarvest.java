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
package mage.sets.onslaught;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
 * @author LevelX2
 */
public class WeirdHarvest extends CardImpl {

    public WeirdHarvest(UUID ownerId) {
        super(ownerId, 299, "Weird Harvest", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");
        this.expansionSetCode = "ONS";

        // Each player may search his or her library for up to X creature cards, reveal those cards, and put them into his or her hand. Then each player who searched his or her library this way shuffles it.
        getSpellAbility().addEffect(new WeirdHarvestEffect());
    }

    public WeirdHarvest(final WeirdHarvest card) {
        super(card);
    }

    @Override
    public WeirdHarvest copy() {
        return new WeirdHarvest(this);
    }
}

class WeirdHarvestEffect extends OneShotEffect {

    public WeirdHarvestEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player may search his or her library for up to X creature cards, reveal those cards, and put them into his or her hand. Then each player who searched his or her library this way shuffles it";
    }

    public WeirdHarvestEffect(final WeirdHarvestEffect effect) {
        super(effect);
    }

    @Override
    public WeirdHarvestEffect copy() {
        return new WeirdHarvestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            int xValue = source.getManaCostsToPay().getX();
            if (xValue > 0) {
                List<Player> usingPlayers = new ArrayList<>();
                this.chooseAndSearchLibrary(usingPlayers, controller, xValue, source, sourceObject, game);
                for (UUID playerId : controller.getInRange()) {
                    if (!playerId.equals(controller.getId())) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            this.chooseAndSearchLibrary(usingPlayers, player, xValue, source, sourceObject, game);
                        }
                    }
                }
                for (Player player : usingPlayers) {
                    player.shuffleLibrary(game);
                }
                return true;
            }
        }
        return false;
    }

    private void chooseAndSearchLibrary(List<Player> usingPlayers, Player player, int xValue, Ability source, MageObject sourceObject, Game game) {
        if (player.chooseUse(Outcome.PutCardInPlay, "Search your library for up " + xValue + " creature cards and put them into your hand?", source, game)) {
            usingPlayers.add(player);
            TargetCardInLibrary target = new TargetCardInLibrary(0, xValue, new FilterCreatureCard());
            if (player.searchLibrary(target, game)) {
                if (target.getTargets().size() > 0) {
                    Cards cards = new CardsImpl(target.getTargets());
                    player.moveCards(cards, null, Zone.HAND, source, game);
                    player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ")", cards, game);
                }
            }
        }
    }

}
