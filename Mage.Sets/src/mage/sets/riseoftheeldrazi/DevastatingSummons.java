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
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author jeffwadsworth
 */
public class DevastatingSummons extends CardImpl<DevastatingSummons> {

    public DevastatingSummons(UUID ownerId) {
        super(ownerId, 140, "Devastating Summons", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        // As an additional cost to cast Devastating Summons, sacrifice X lands.
        this.getSpellAbility().addCost(new SacrificeXTargetCost(new FilterControlledLandPermanent("lands"), true));
        
        // Put two X/X red Elemental creature tokens onto the battlefield.
        this.getSpellAbility().addEffect(new DevastatingSummonsEffect());
    }

    public DevastatingSummons(final DevastatingSummons card) {
        super(card);
    }

    @Override
    public DevastatingSummons copy() {
        return new DevastatingSummons(this);
    }
}

class DevastatingSummonsEffect extends OneShotEffect<DevastatingSummonsEffect> {

    public DevastatingSummonsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put two X/X red Elemental creature tokens onto the battlefield";
    }

    public DevastatingSummonsEffect(final DevastatingSummonsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ElementalToken token = new ElementalToken();
        
        token.getPower().initValue(new GetXValue().calculate(game, source));
        token.getToughness().initValue(new GetXValue().calculate(game, source));
        
        token.putOntoBattlefield(2, game, source.getSourceId(), source.getControllerId());
        
        return true;
    }

    @Override
    public DevastatingSummonsEffect copy() {
        return new DevastatingSummonsEffect(this);
    }
}

class ElementalToken extends Token {

    public ElementalToken() {
        super("Elemental", "X/X red Elemental creature");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.RED;
        subtype.add("Elemental");
    }
}
