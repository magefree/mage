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
package mage.sets.odyssey;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.effects.common.continious.SetCardColorTargetEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author magenoxx_at_gmail.com
 */
public class WildMongrel extends CardImpl<WildMongrel> {

    public WildMongrel(UUID ownerId) {
        super(ownerId, 283, "Wild Mongrel", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "ODY";
        this.subtype.add("Hound");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Discard a card: Wild Mongrel gets +1/+1 and becomes the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(1, 1, Constants.Duration.EndOfTurn), new DiscardTargetCost(new TargetCardInHand()));
        ability.addEffect(new ChangeColorEffect());
        this.addAbility(ability);
    }

    public WildMongrel(final WildMongrel card) {
        super(card);
    }

    @Override
    public WildMongrel copy() {
        return new WildMongrel(this);
    }
}

class ChangeColorEffect extends OneShotEffect<ChangeColorEffect> {

    public ChangeColorEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "becomes the color of your choice until end of turn";
    }

    public ChangeColorEffect(final ChangeColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent wildMongrel = game.getPermanent(source.getSourceId());
        if (player != null && wildMongrel != null) {
            ChoiceColor colorChoice = new ChoiceColor();
            if (player.choose(Constants.Outcome.Neutral, colorChoice, game)) {
                game.informPlayers(wildMongrel.getName() + ": " + player.getName() + " has chosen " + colorChoice.getChoice());
                ContinuousEffect effect = new SetCardColorTargetEffect(colorChoice.getColor(), Constants.Duration.EndOfTurn, "is " + colorChoice.getChoice());
                effect.setTargetPointer(new FixedTarget(source.getSourceId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public ChangeColorEffect copy() {
        return new ChangeColorEffect(this);
    }
}
