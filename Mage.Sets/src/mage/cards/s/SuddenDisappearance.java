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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author BetaSteward
 */
public class SuddenDisappearance extends CardImpl {

    public SuddenDisappearance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}");

        // Exile all nonland permanents target player controls. Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new SuddenDisappearanceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public SuddenDisappearance(final SuddenDisappearance card) {
        super(card);
    }

    @Override
    public SuddenDisappearance copy() {
        return new SuddenDisappearance(this);
    }
}

class SuddenDisappearanceEffect extends OneShotEffect {

    private static FilterNonlandPermanent filter = new FilterNonlandPermanent();

    public SuddenDisappearanceEffect() {
        super(Outcome.Exile);
        staticText = "Exile all nonland permanents target player controls. Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step";
    }

    public SuddenDisappearanceEffect(final SuddenDisappearanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Set<Card> permsSet = new HashSet<>(game.getBattlefield().getAllActivePermanents(filter, source.getFirstTarget(), game));
            if (permsSet.size() > 0) {
                controller.moveCardsToExile(permsSet, source, game, true, source.getSourceId(), sourceObject.getIdName());
                Cards targets = new CardsImpl();
                for (Card card : permsSet) {
                    targets.add(card.getId());
                }
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                effect.setText("Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTargets(targets, game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
            }
            return true;
        }

        return false;
    }

    @Override
    public SuddenDisappearanceEffect copy() {
        return new SuddenDisappearanceEffect(this);
    }

}
