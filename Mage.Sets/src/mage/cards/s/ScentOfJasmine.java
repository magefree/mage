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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public class ScentOfJasmine extends CardImpl {

    public ScentOfJasmine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Reveal any number of white cards in your hand. You gain 2 life for each card revealed this way.
        Effect effect = new ScentOfJasmineEffect();
        effect.setText("Reveal any number of white cards in your hand. You gain 2 life for each card revealed this way.");
        this.getSpellAbility().addEffect(effect);
    }

    public ScentOfJasmine(final ScentOfJasmine card) {
        super(card);
    }

    @Override
    public ScentOfJasmine copy() {
        return new ScentOfJasmine(this);
    }
}

class ScentOfJasmineEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("white cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ScentOfJasmineEffect() {
        super(Outcome.GainLife);
    }

    public ScentOfJasmineEffect(final ScentOfJasmineEffect effect) {
        super(effect);
    }

    @Override
    public ScentOfJasmineEffect copy() {
        return new ScentOfJasmineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        if (player.getHand().count(filter, game) > 0) {
            TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
            if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                for (UUID uuid : target.getTargets()) {
                    cards.add(player.getHand().get(uuid, game));
                }
                player.revealCards("cards", cards, game);
                player.gainLife(cards.getCards(game).size() * 2, game);
            }
        }
        return true;
    }
}
