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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class FoulRenewal extends CardImpl {

    public FoulRenewal(UUID ownerId) {
        super(ownerId, 101, "Foul Renewal", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{B}");
        this.expansionSetCode = "DTK";

        // Return target creature card from your graveyard to your hand. Target creature gets -X/-X until end of turn, where X is the toughness of the card returned this way.
        this.getSpellAbility().addEffect(new FoulRenewalEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public FoulRenewal(final FoulRenewal card) {
        super(card);
    }

    @Override
    public FoulRenewal copy() {
        return new FoulRenewal(this);
    }
}

class FoulRenewalEffect extends OneShotEffect {

    public FoulRenewalEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target creature card from your graveyard to your hand. Target creature gets -X/-X until end of turn, where X is the toughness of the card returned this way";
    }

    public FoulRenewalEffect(final FoulRenewalEffect effect) {
        super(effect);
    }

    @Override
    public FoulRenewalEffect copy() {
        return new FoulRenewalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null) {
                int xValue = card.getToughness().getValue();
                controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD);
                if (xValue != 0) {
                    ContinuousEffect effect = new BoostTargetEffect(xValue,xValue, Duration.EndOfTurn);
                    effect.setTargetPointer(new FixedTarget(source.getTargets().get(1).getFirstTarget()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
