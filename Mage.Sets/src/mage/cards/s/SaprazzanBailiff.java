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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class SaprazzanBailiff extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("artifact and enchantment cards");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public SaprazzanBailiff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Saprazzan Bailiff enters the battlefield, exile all artifact and enchantment cards from all graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SaprazzanBailiffEffect()));

        // When Saprazzan Bailiff leaves the battlefield, return all artifact and enchantment cards from all graveyards to their owners' hands.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnToHandFromGraveyardAllEffect(filter).setText("return all artifact and enchantment cards from all graveyards to their owners' hands"), false));
    }

    public SaprazzanBailiff(final SaprazzanBailiff card) {
        super(card);
    }

    @Override
    public SaprazzanBailiff copy() {
        return new SaprazzanBailiff(this);
    }
}

class SaprazzanBailiffEffect extends OneShotEffect {

    public SaprazzanBailiffEffect() {
        super(Outcome.Detriment);
        staticText = "exile all artifact and enchantment cards from all graveyards";
    }

    @Override
    public SaprazzanBailiffEffect copy() {
        return new SaprazzanBailiffEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getGraveyard().copy()) {
                    Card card = game.getCard(cid);
                    if (card != null && (card.isArtifact() || card.isEnchantment())) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                    }
                }
            }
        }
        return true;
    }
}
