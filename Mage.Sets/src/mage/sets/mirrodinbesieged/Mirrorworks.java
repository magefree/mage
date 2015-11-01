/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mirrorworks extends CardImpl {

    private final static FilterArtifactPermanent filter = new FilterArtifactPermanent("another nontoken artifact");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public Mirrorworks(UUID ownerId) {
        super(ownerId, 114, "Mirrorworks", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "MBS";

        // Whenever another nontoken artifact enters the battlefield under your control, you may pay {2}.
        // If you do, put a token that's a copy of that artifact onto the battlefield.
        Effect effect = new DoIfCostPaid(new PutTokenOntoBattlefieldCopyTargetEffect(),
                new ManaCostsImpl("{2}"), "Put a token that's a copy of that artifact onto the battlefield?");
        effect.setText("you may pay {2}. If you do, put a token that's a copy of that artifact onto the battlefield");
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, effect, filter, false, SetTargetPointer.PERMANENT, null));
    }

    public Mirrorworks(final Mirrorworks card) {
        super(card);
    }

    @Override
    public Mirrorworks copy() {
        return new Mirrorworks(this);
    }

}
