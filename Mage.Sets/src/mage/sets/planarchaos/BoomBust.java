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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.TargetPermanent;

public class BoomBust extends SplitCard {

    private static final FilterLandPermanent filter1 = new FilterLandPermanent("land you control");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("land you don't control");

    static {
        filter1.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public BoomBust(UUID ownerId) {
        super(ownerId, 112, "Boom", "Bust", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{1}{R}", "{5}{R}", false);
        this.expansionSetCode = "PLC";

        // Boom
        // Destroy target land you control and target land you don't control.
        Effect effect = new DestroyTargetEffect(false, true);
        effect.setText("Destroy target land you control and target land you don't control");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter1));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter2));

        // Bust
        // Destroy all lands.
        getRightHalfCard().getSpellAbility().addEffect(new DestroyAllEffect(new FilterLandPermanent()));

    }

    public BoomBust(final BoomBust card) {
        super(card);
    }

    @Override
    public BoomBust copy() {
        return new BoomBust(this);
    }
}
