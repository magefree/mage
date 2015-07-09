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

package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.cards.SplitCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author Wehk
 */
public class PureSimple extends SplitCard {

    private static final FilterPermanent filterAura = new FilterPermanent("aura");
    private static final FilterPermanent filterEquipment = new FilterPermanent("equipment");
    private static final FilterPermanent filterMulticolor = new FilterPermanent("multicolor permanent");
    
    static {
        filterAura.add(new SubtypePredicate("Aura"));
        filterEquipment.add(new SubtypePredicate("Equipment"));
        filterMulticolor.add(new MulticoloredPredicate());
    }
    
    public PureSimple(UUID ownerId) {
        super(ownerId, 154, "Pure", "Simple", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{R}{G}", "{1}{G}{W}", true);
        this.expansionSetCode = "DIS";

        // Pure
        // Destroy all Auras
        getLeftHalfCard().getSpellAbility().addEffect(new DestroyAllEffect(filterAura));
        // and Equipment.
        getLeftHalfCard().getSpellAbility().addEffect(new DestroyAllEffect(filterEquipment));

        // Simple
        // Destroy target multicolored permanent.
        getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filterMulticolor));
    }

    public PureSimple(final PureSimple card) {
        super(card);
    }

    @Override
    public PureSimple copy() {
        return new PureSimple(this);
    }
}
