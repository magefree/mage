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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ReasonBelieve extends SplitCard {

    public ReasonBelieve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{U}", "{4}{G}", SpellAbilityType.SPLIT_AFTERMATH);

        // Reason
        // Scry 3.
        getLeftHalfCard().getSpellAbility().addEffect(new ScryEffect(3));

        // Believe
        // Aftermath
        ((CardImpl) (getRightHalfCard())).addAbility(new AftermathAbility().setRuleAtTheTop(true));
        // Look at the top card of your library. You may put it onto the battlefield if it's a creature card. If you don't, put it into your hand.
        getRightHalfCard().getSpellAbility().addEffect(new BelieveEffect());

    }

    public ReasonBelieve(final ReasonBelieve card) {
        super(card);
    }

    @Override
    public ReasonBelieve copy() {
        return new ReasonBelieve(this);
    }
}

class BelieveEffect extends OneShotEffect {

    BelieveEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Look at the top card of your library. You may put it onto the battlefield if it's a creature card. If you don't, put it into your hand";
    }

    BelieveEffect(final BelieveEffect effect) {
        super(effect);
    }

    @Override
    public BelieveEffect copy() {
        return new BelieveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                if (card.isCreature() && controller.chooseUse(outcome, "Put " + card.getIdName() + " onto the battlefield?", source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else {
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
