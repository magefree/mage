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
package mage.sets.planeshift;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox
 */
public class Stratadon extends CardImpl {

    public Stratadon(UUID ownerId) {
        super(ownerId, 135, "Stratadon", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{10}");
        this.expansionSetCode = "PLS";
        this.subtype.add("Beast");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Domain - Stratadon costs {1} less to cast for each basic land type among lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new StratadonCostReductionEffect()));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        super.adjustCosts(ability, game);
        CardUtil.adjustCost((SpellAbility)ability, new DomainValue().calculate(game, ability, null));
    }


    public Stratadon(final Stratadon card) {
        super(card);
    }

    @Override
    public Stratadon copy() {
        return new Stratadon(this);
    }
}

// Dummy to get the text on the card.
class StratadonCostReductionEffect extends OneShotEffect {
    private static final String effectText = "Domain - {this} costs {1} less to cast for each basic land type among lands you control.";

    StratadonCostReductionEffect() {
        super(Outcome.Benefit);
        this.staticText = effectText;
    }

    StratadonCostReductionEffect(StratadonCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public StratadonCostReductionEffect copy() {
        return new StratadonCostReductionEffect(this);
    }

}
