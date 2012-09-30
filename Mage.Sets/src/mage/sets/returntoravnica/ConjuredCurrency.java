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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.continious.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

/**
 * @author LevelX2
 */
public class ConjuredCurrency extends CardImpl<ConjuredCurrency> {

    private static final FilterPermanent filter = new FilterPermanent("permanent you neither own nor control");

    static {
        filter.add(new OwnerPredicate(Constants.TargetController.NOT_YOU));
        filter.add(new ControllerPredicate(Constants.TargetController.NOT_YOU));
    }
    private static final String rule = "you may exchange control of {this} and target permanent you neither own nor control";

    public ConjuredCurrency(UUID ownerId) {
        super(ownerId, 33, "Conjured Currency", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);

        // At the beginning of your upkeep, you may exchange control of Conjured Currency and target permanent you neither own nor control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ExchangeControlTargetEffect(Constants.Duration.EndOfGame, rule, true), Constants.TargetController.YOU, true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public ConjuredCurrency(final ConjuredCurrency card) {
        super(card);
    }

    @Override
    public ConjuredCurrency copy() {
        return new ConjuredCurrency(this);
    }
}
