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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class GrimCaptainsCall extends CardImpl {

    public GrimCaptainsCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Return a Pirate card from your graveyard to your hand, then do the same for Vampire, Dinosaur, and Merfolk.
        this.getSpellAbility().addEffect(new GrimCaptainsCallEffect());
    }

    public GrimCaptainsCall(final GrimCaptainsCall card) {
        super(card);
    }

    @Override
    public GrimCaptainsCall copy() {
        return new GrimCaptainsCall(this);
    }
}

class GrimCaptainsCallEffect extends OneShotEffect {

    public GrimCaptainsCallEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return a Pirate card from your graveyard to your hand, then do the same for Vampire, Dinosaur, and Merfolk";
    }

    public GrimCaptainsCallEffect(final GrimCaptainsCallEffect effect) {
        super(effect);
    }

    @Override
    public GrimCaptainsCallEffect copy() {
        return new GrimCaptainsCallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            returnToHand(game, SubType.PIRATE, controller, source);
            returnToHand(game, SubType.VAMPIRE, controller, source);
            returnToHand(game, SubType.DINOSAUR, controller, source);
            returnToHand(game, SubType.MERFOLK, controller, source);
            return true;
        }
        return false;
    }

    private void returnToHand(Game game, SubType subType, Player controller, Ability source) {
        FilterCreatureCard filter = new FilterCreatureCard(subType.getDescription() + " card");
        filter.add(new SubtypePredicate(subType));
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(new FilterCreatureCard(filter));
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
            if (controller.chooseTarget(outcome, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.moveCards(card, Zone.HAND, source, game);
                }
            }
        }
    }
}
