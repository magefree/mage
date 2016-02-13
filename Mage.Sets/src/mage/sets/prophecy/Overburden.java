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
package mage.sets.prophecy;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenPermanentEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author Quercitron
 */
public class Overburden extends CardImpl {

    private static final FilterCreaturePermanent ENTERS_BATTLEFIELD_FILTER = new FilterCreaturePermanent("a nontoken creature");

    private static final FilterControlledLandPermanent RETURN_FILTER = new FilterControlledLandPermanent("a land");

    static {
        ENTERS_BATTLEFIELD_FILTER.add(Predicates.not(new TokenPredicate()));
    }

    public Overburden(UUID ownerId) {
        super(ownerId, 39, "Overburden", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "PCY";

        // Whenever a player puts a nontoken creature onto the battlefield,
        // that player returns a land he or she controls to its owner's hand.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new ReturnToHandChosenPermanentEffect(RETURN_FILTER),
                ENTERS_BATTLEFIELD_FILTER,
                false,
                SetTargetPointer.PLAYER,
                "Whenever a player puts a nontoken creature onto the battlefield," +
                        " that player returns a land he or she controls to its owner's hand."));
    }

    public Overburden(final Overburden card) {
        super(card);
    }

    @Override
    public Overburden copy() {
        return new Overburden(this);
    }
}
