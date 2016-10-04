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
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public class ScorchedEarth extends CardImpl {

    public ScorchedEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");

        // As an additional cost to cast Scorched Earth, discard X land cards.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new ScorchedEarthRuleEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Destroy X target lands.
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy X target lands");
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(effect);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int xValue = ability.getManaCostsToPay().getX();
            ability.addTarget(new TargetLandPermanent(xValue, xValue, new FilterLandPermanent(), false));
        }
    }

    public ScorchedEarth(final ScorchedEarth card) {
        super(card);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            ability.addCost(new DiscardTargetCost(new TargetCardInHand(xValue, xValue, new FilterLandCard("land cards"))));
        }
    }

    @Override
    public ScorchedEarth copy() {
        return new ScorchedEarth(this);
    }
}

class ScorchedEarthRuleEffect extends OneShotEffect {

    public ScorchedEarthRuleEffect() {
        super(Outcome.Benefit);
        this.staticText = "As an additional cost to cast {this}, discard X land cards";
    }

    public ScorchedEarthRuleEffect(final ScorchedEarthRuleEffect effect) {
        super(effect);
    }

    @Override
    public ScorchedEarthRuleEffect copy() {
        return new ScorchedEarthRuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}