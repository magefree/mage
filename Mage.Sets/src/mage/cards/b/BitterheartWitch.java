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
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class BitterheartWitch extends CardImpl {

    public BitterheartWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        this.addAbility(DeathtouchAbility.getInstance());

        // When Bitterheart Witch dies, you may search your library for a Curse card, put it onto the battlefield attached to target player, then shuffle your library.
        Ability ability = new DiesTriggeredAbility(new BitterheartWitchEffect(), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public BitterheartWitch(final BitterheartWitch card) {
        super(card);
    }

    @Override
    public BitterheartWitch copy() {
        return new BitterheartWitch(this);
    }
}

class BitterheartWitchEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Curse card in your library");

    static {
        filter.add(new SubtypePredicate(SubType.CURSE));
    }

    public BitterheartWitchEffect() {
        super(Outcome.Detriment);
        staticText = "you may search your library for a Curse card, put it onto the battlefield attached to target player, then shuffle your library";
    }

    public BitterheartWitchEffect(final BitterheartWitchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            TargetCardInLibrary targetCard = new TargetCardInLibrary(filter);
            if (player.searchLibrary(targetCard, game)) {
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    game.getState().setValue("attachTo:" + card.getId(), targetPlayer.getId());
                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), source.getControllerId());
                    targetPlayer.addAttachment(card.getId(), game);
                }
                player.shuffleLibrary(source, game);
                return true;
            }
            player.shuffleLibrary(source, game);
        }
        return false;
    }

    @Override
    public BitterheartWitchEffect copy() {
        return new BitterheartWitchEffect(this);
    }

}
