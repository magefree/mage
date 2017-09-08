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
package mage.cards.h;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class HourOfEternity extends CardImpl {

    public HourOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{U}{U}{U}");

        // Exile X target creature cards from your graveyard. For each card exiled this way, create a token that's a copy of that card, except it's a 4/4 black Zombie.
        this.getSpellAbility().addEffect(new HourOfEternityEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, new FilterCreatureCard("creature cards from your graveyard")));
    }

    public HourOfEternity(final HourOfEternity card) {
        super(card);
    }

    @Override
    public HourOfEternity copy() {
        return new HourOfEternity(this);
    }
}

class HourOfEternityEffect extends OneShotEffect {

    HourOfEternityEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile X target creature cards from your graveyard. For each card exiled this way, create a token that's a copy of that card, except it's a 4/4 black Zombie";
    }

    HourOfEternityEffect(final HourOfEternityEffect effect) {
        super(effect);
    }

    @Override
    public HourOfEternityEffect copy() {
        return new HourOfEternityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToExile = new HashSet<>(this.getTargetPointer().getTargets(game, source).size());
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Card card = controller.getGraveyard().get(targetId, game);
                if (card != null) {
                    cardsToExile.add(card);
                }
            }
            controller.moveCardsToExile(cardsToExile, source, game, true, null, "");
            for (Card card : cardsToExile) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                    EmptyToken token = new EmptyToken();
                    CardUtil.copyTo(token).from(card);
                    token.getPower().modifyBaseValue(4);
                    token.getToughness().modifyBaseValue(4);
                    token.getColor(game).setColor(ObjectColor.BLACK);
                    token.getSubtype(game).clear();
                    token.getSubtype(game).add("Zombie");
                    token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
                }
            }
            return true;
        }
        return false;
    }
}
