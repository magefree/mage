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
package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class InduceDespair extends CardImpl<InduceDespair> {
    
    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card from your hand.");

    public InduceDespair(UUID ownerId) {
        super(ownerId, 114, "Induce Despair", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{B}");
        this.expansionSetCode = "ROE";

        this.color.setBlack(true);

        // As an additional cost to cast Induce Despair, reveal a creature card from your hand.
        // Target creature gets -X/-X until end of turn, where X is the revealed card's converted mana cost.
        this.getSpellAbility().addEffect(new InduceDespairEffect());
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(new TargetCardInHand(filter)));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public InduceDespair(final InduceDespair card) {
        super(card);
    }

    @Override
    public InduceDespair copy() {
        return new InduceDespair(this);
    }
}

class InduceDespairEffect extends OneShotEffect<InduceDespairEffect> {

    public InduceDespairEffect() {
        super(Constants.Outcome.UnboostCreature);
        staticText = "Target creature gets -X/-X until end of turn, where X is the revealed card's converted mana cost";
    }

    public InduceDespairEffect(InduceDespairEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (cost != null) {
            int CMC = -1 * cost.convertedManaCosts;
            if (creature != null) {
                creature.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostSourceEffect(CMC, CMC, Constants.Duration.EndOfTurn)), game);
            }
        }
        return true;
    }

    @Override
    public InduceDespairEffect copy() {
        return new InduceDespairEffect(this);
    }

}
