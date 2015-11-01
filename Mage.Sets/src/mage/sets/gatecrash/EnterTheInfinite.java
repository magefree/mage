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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.turn.Step;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class EnterTheInfinite extends CardImpl {

    public EnterTheInfinite(UUID ownerId) {
        super(ownerId, 34, "Enter the Infinite", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{8}{U}{U}{U}{U}");
        this.expansionSetCode = "GTC";

        // Draw cards equal to the number of cards in your library,
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new CardsInControllerLibraryCount()));
        //then put a card from your hand on top of your library.
        this.getSpellAbility().addEffect(new PutCardOnLibraryEffect());
        //You have no maximum hand size until your next turn.
        this.getSpellAbility().addEffect(new MaximumHandSizeEffect());
    }

    public EnterTheInfinite(final EnterTheInfinite card) {
        super(card);
    }

    @Override
    public EnterTheInfinite copy() {
        return new EnterTheInfinite(this);
    }
}

class CardsInControllerLibraryCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            Player controller = game.getPlayer(sourceAbility.getControllerId());
            if (controller != null) {
                return controller.getLibrary().size();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInControllerLibraryCount();
    }

    @Override
    public String getMessage() {
        return "card in your library";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class PutCardOnLibraryEffect extends OneShotEffect {

    public PutCardOnLibraryEffect() {
        super(Outcome.DrawCard);
        staticText = "Then put a card from your hand on top of your library";
    }

    public PutCardOnLibraryEffect(final PutCardOnLibraryEffect effect) {
        super(effect);
    }

    @Override
    public PutCardOnLibraryEffect copy() {
        return new PutCardOnLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInHand target = new TargetCardInHand();
            controller.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Card card = controller.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                controller.putCardsOnTopOfLibrary(new CardsImpl(card), game, source, false);
            }
            return true;
        }
        return false;
    }
}

class MaximumHandSizeEffect extends MaximumHandSizeControllerEffect {

    public MaximumHandSizeEffect() {
        super(Integer.MAX_VALUE, Duration.Custom, MaximumHandSizeControllerEffect.HandSizeModification.SET);
        staticText = "You have no maximum hand size until your next turn";
    }

    public MaximumHandSizeEffect(final MaximumHandSizeEffect effect) {
        super(effect);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UNTAP && game.getStep().getStepPart() == Step.StepPart.PRE) {
            if (game.getActivePlayerId().equals(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MaximumHandSizeEffect copy() {
        return new MaximumHandSizeEffect(this);
    }
}
