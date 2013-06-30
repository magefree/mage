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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class MorbidBloom extends CardImpl<MorbidBloom> {

    public MorbidBloom(UUID ownerId) {
        super(ownerId, 94, "Morbid Bloom", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{4}{B}{G}");
        this.expansionSetCode = "ARB";

        this.color.setGreen(true);
        this.color.setBlack(true);

        // Exile target creature card from a graveyard, then put X 1/1 green Saproling creature tokens onto the battlefield, where X is the exiled card's toughness.
        this.getSpellAbility().addEffect(new MorbidBloomEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature from a graveyard")));
        
    }

    public MorbidBloom(final MorbidBloom card) {
        super(card);
    }

    @Override
    public MorbidBloom copy() {
        return new MorbidBloom(this);
    }
}

class MorbidBloomEffect extends OneShotEffect<MorbidBloomEffect> {

    public MorbidBloomEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Exile target creature card from a graveyard, then put X 1/1 green Saproling creature tokens onto the battlefield, where X is the exiled card's toughness";
    }

    public MorbidBloomEffect(final MorbidBloomEffect effect) {
        super(effect);
    }

    @Override
    public MorbidBloomEffect copy() {
        return new MorbidBloomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card targetCreatureCard = game.getCard(source.getFirstTarget());
        if (targetCreatureCard != null) {
            targetCreatureCard.moveToExile(null, null, source.getId(), game);
            int toughness = targetCreatureCard.getToughness().getValue();
            SaprolingToken token = new SaprolingToken();
            return token.putOntoBattlefield(toughness, game, source.getId(), source.getControllerId());
        }
        return false;
    }
}
