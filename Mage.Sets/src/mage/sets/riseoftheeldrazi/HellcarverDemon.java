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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.players.Player;
import mage.game.permanent.Permanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterNonlandCard;
import mage.target.common.TargetCardInExile;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */

public class HellcarverDemon extends CardImpl<HellcarverDemon> {

    public HellcarverDemon(UUID ownerId) {
        super(ownerId, 113, "Hellcarver Demon", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Demon");

        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hellcarver Demon deals combat damage to a player, sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HellcarverDemonEffect(), false));
    }

    public HellcarverDemon(final HellcarverDemon card) {
        super(card);
    }

    @Override
    public HellcarverDemon copy() {
        return new HellcarverDemon(this);
    }
}

class HellcarverDemonEffect extends OneShotEffect<HellcarverDemonEffect> {

    private final static FilterControlledPermanent filterPermanents = new FilterControlledPermanent("Permanent");
    private static FilterNonlandCard filter = new FilterNonlandCard("nonland card exiled with Hellcarver Demon");

    public HellcarverDemonEffect() {
        super(Outcome.PlayForFree);
        staticText = "sacrifice all other permanents you control and discard your hand. Exile the top six cards of your library. You may cast any number of nonland cards exiled this way without paying their mana costs.";
    }

    public HellcarverDemonEffect(final HellcarverDemonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
    Player player = game.getPlayer(source.getControllerId());
        Permanent hellcarverDemon = game.getPermanent(source.getSourceId());

        for (Permanent permanent: game.getBattlefield().getActivePermanents(filterPermanents, source.getControllerId(), game)) {
            if (permanent != hellcarverDemon) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }

        if (player != null && player.getHand().size() > 0) {
            int cardsInHand = player.getHand().size();
            player.discard(cardsInHand, source, game);
        }

        for (int i = 0; i < 6; i++) {
            if (player != null && player.getLibrary().size() > 0) {
                Card topCard = player.getLibrary().getFromTop(game);
                topCard.moveToExile(source.getSourceId(), "Cards exiled by Hellcarver Demon", source.getId(), game);
            }
        }

        while (player != null && player.chooseUse(Outcome.PlayForFree, "Cast another nonland card exiled with Hellcarver Demon without paying that card's mana cost?", game)) {
            TargetCardInExile target = new TargetCardInExile(filter, source.getSourceId());
            while (player.choose(Outcome.PlayForFree, game.getExile().getExileZone(source.getSourceId()), target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        game.getExile().removeCard(card, game);
                        player.cast(card.getSpellAbility(), game, true);
                    }
                    target.clearChosen();
            }
            return true;
        }     
        return false;
    }

    @Override
    public HellcarverDemonEffect copy() {
    return new HellcarverDemonEffect(this);
    }
}
