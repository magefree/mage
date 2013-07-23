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
package mage.sets.limitedalpha;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author KholdFuzion

 */
public class Resurrection extends CardImpl<Resurrection> {

    public Resurrection(UUID ownerId) {
        super(ownerId, 218, "Resurrection", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");
        this.expansionSetCode = "LEA";

        this.color.setWhite(true);

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ResurrectionEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));

    }

    public Resurrection(final Resurrection card) {
        super(card);
    }

    @Override
    public Resurrection copy() {
        return new Resurrection(this);
    }
}

class ResurrectionEffect extends OneShotEffect<ResurrectionEffect> {

    public ResurrectionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return target creature card from your graveyard to the battlefield.";
    }

    public ResurrectionEffect(final ResurrectionEffect effect) {
        super(effect);
    }

    @Override
    public ResurrectionEffect copy() {
        return new ResurrectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player player = game.getPlayer(card.getOwnerId());
        if (card != null && player != null) {
            card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), source.getControllerId());
            return true;
        }

        return false;
    }
}
