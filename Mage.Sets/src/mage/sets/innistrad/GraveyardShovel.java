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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public class GraveyardShovel extends CardImpl<GraveyardShovel> {

    public GraveyardShovel(UUID ownerId) {
        super(ownerId, 225, "Graveyard Shovel", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "ISD";

        // {2}, {tap}: Target player exiles a card from his or her graveyard. If it's a creature card, you gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GraveyardShovelEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public GraveyardShovel(final GraveyardShovel card) {
        super(card);
    }

    @Override
    public GraveyardShovel copy() {
        return new GraveyardShovel(this);
    }
}

class GraveyardShovelEffect extends OneShotEffect<GraveyardShovelEffect> {

    public GraveyardShovelEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player exiles a card from his or her graveyard. If it's a creature card, you gain 2 life";
    }

    public GraveyardShovelEffect(final GraveyardShovelEffect effect) {
        super(effect);
    }

    @Override
    public GraveyardShovelEffect copy() {
        return new GraveyardShovelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controller != null) {
            TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
            if (targetPlayer.chooseTarget(Outcome.Exile, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    targetPlayer.getGraveyard().remove(card);
                    card.moveToExile(null, "", source.getId(), game);
                    if (card.getCardType().contains(CardType.CREATURE)) {
                        controller.gainLife(2, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
