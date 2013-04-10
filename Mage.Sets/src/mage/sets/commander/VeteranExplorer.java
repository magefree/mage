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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class VeteranExplorer extends CardImpl<VeteranExplorer> {

    public VeteranExplorer(UUID ownerId) {
        super(ownerId, 177, "Veteran Explorer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "CMD";
        this.subtype.add("Human");
        this.subtype.add("Soldier");
        this.subtype.add("Scout");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Veteran Explorer dies, each player may search his or her library for up to two basic land cards and put them onto the battlefield. Then each player who searched his or her library this way shuffles it.
        this.addAbility(new DiesTriggeredAbility(new VeteranExplorerEffect()));
    }

    public VeteranExplorer(final VeteranExplorer card) {
        super(card);
    }

    @Override
    public VeteranExplorer copy() {
        return new VeteranExplorer(this);
    }
}
class VeteranExplorerEffect extends OneShotEffect<VeteranExplorerEffect> {

    public VeteranExplorerEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player may search his or her library for up to two basic land cards and put them onto the battlefield. Then each player who searched his or her library this way shuffles it";
    }

    public VeteranExplorerEffect(final VeteranExplorerEffect effect) {
        super(effect);
    }

    @Override
    public VeteranExplorerEffect copy() {
        return new VeteranExplorerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Player> usingPlayers = new ArrayList<Player>();
            this.chooseAndSearchLibrary(usingPlayers, controller, source, game);
            for (UUID playerId: controller.getInRange()) {
                if (!playerId.equals(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        this.chooseAndSearchLibrary(usingPlayers, player, source, game);
                    }
                }
            }
            for (Player player: usingPlayers) {
                player.getLibrary().shuffle();
            }
            return true;
        }
        return false;
    }

    private void chooseAndSearchLibrary(List<Player> usingPlayers, Player player, Ability source, Game game) {
        if (player.chooseUse(Outcome.PutCardInPlay, "Search your library for up to two basic land cards and put them onto the battlefield?", game)) {
            usingPlayers.add(player);
            TargetCardInLibrary target = new TargetCardInLibrary(0, 2, new FilterBasicLandCard());
            if (player.searchLibrary(target, game)) {
                if (target.getTargets().size() > 0) {
                    for (UUID cardId: (List<UUID>)target.getTargets()) {
                        Card card = player.getLibrary().getCard(cardId, game);
                        if (card != null) {
                            card.putOntoBattlefield(game, Constants.Zone.LIBRARY, source.getId(), player.getId());
                        }
                    }
                }
            }
        }
    }
    
}
