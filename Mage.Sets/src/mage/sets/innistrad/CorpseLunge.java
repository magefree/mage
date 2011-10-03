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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class CorpseLunge extends CardImpl<CorpseLunge> {

    public CorpseLunge(UUID ownerId) {
        super(ownerId, 93, "Corpse Lunge", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "ISD";

        this.color.setBlack(true);

        // As an additional cost to cast Corpse Lunge, exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard"))));
        // Corpse Lunge deals damage equal to the exiled card's power to target creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CorpseLungeEffect());

    }

    public CorpseLunge(final CorpseLunge card) {
        super(card);
    }

    @Override
    public CorpseLunge copy() {
        return new CorpseLunge(this);
    }
}

class CorpseLungeEffect extends OneShotEffect<CorpseLungeEffect> {

    public CorpseLungeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "{this} deals damage equal to the exiled card's power to target creature";
    }

    public CorpseLungeEffect(final CorpseLungeEffect effect) {
        super(effect);
    }

    @Override
    public CorpseLungeEffect copy() {
        return new CorpseLungeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof ExileFromGraveCost) {
                Card card = game.getCard(cost.getTargets().getFirstTarget());
                if (card != null) {
                    amount = card.getPower().getValue();
                }
                break;
            }
        }
        if (amount > 0) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.damage(amount, source.getSourceId(), game, true, false);
                return true;
            }
        }
        return false;
    }
}
