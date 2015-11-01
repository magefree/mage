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
package mage.sets.zendikar;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class ExplorersScope extends CardImpl {

    public ExplorersScope(UUID ownerId) {
        super(ownerId, 202, "Explorer's Scope", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Equipment");

        // Whenever equipped creature attacks, look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped.
        this.addAbility(new AttacksAttachedTriggeredAbility(new ExplorersScopeEffect()));

        // Equip ({1}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    public ExplorersScope(final ExplorersScope card) {
        super(card);
    }

    @Override
    public ExplorersScope copy() {
        return new ExplorersScope(this);
    }
}

class ExplorersScopeEffect extends OneShotEffect {

    public ExplorersScopeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped";
    }

    public ExplorersScopeEffect(final ExplorersScopeEffect effect) {
        super(effect);
    }

    @Override
    public ExplorersScopeEffect copy() {
        return new ExplorersScopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl();
            cards.add(card);
            controller.lookAtCards(sourceObject.getIdName(), cards, game);
            if (card.getCardType().contains(CardType.LAND)) {
                String message = "Put " + card.getLogName() + " onto the battlefield tapped?";
                if (controller.chooseUse(Outcome.PutLandInPlay, message, source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                }
            }
        }
        return true;
    }
}
