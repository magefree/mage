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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class ExplorersScope extends CardImpl<ExplorersScope> {

    public ExplorersScope(UUID ownerId) {
        super(ownerId, 202, "Explorer's Scope", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Equipment");

        this.addAbility(new ExplorersScopeTriggeredAbiltity());
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

class ExplorersScopeTriggeredAbiltity extends TriggeredAbilityImpl<ExplorersScopeTriggeredAbiltity> {

    public ExplorersScopeTriggeredAbiltity() {
        super(Zone.BATTLEFIELD, new ExplorersScopeEffect());
    }

    public ExplorersScopeTriggeredAbiltity(final ExplorersScopeTriggeredAbiltity abiltity) {
        super(abiltity);
    }

    @Override
    public ExplorersScopeTriggeredAbiltity copy() {
        return new ExplorersScopeTriggeredAbiltity(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.sourceId);
        if (equipment != null && equipment.getAttachedTo() != null
                && event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                && event.getSourceId().equals(equipment.getAttachedTo())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks, look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped.";
    }
}

class ExplorersScopeEffect extends OneShotEffect<ExplorersScopeEffect> {

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
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Card card = player.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl();
            cards.add(card);
            player.lookAtCards("Explorer's Scope", cards, game);
            if (card.getCardType().contains(CardType.LAND)) {
                String message = "Put " + card.getName() + " onto the battlefield tapped?";
                if (player.chooseUse(Outcome.PutLandInPlay, message, game)) {
                    if (card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), source.getControllerId())) {
                        Permanent permanent = game.getPermanent(card.getId());
                        if (permanent != null) {
                            permanent.setTapped(true);
                        }
                    }
                }
            }
        }
        return true;
    }
}