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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public class RoughTumble extends SplitCard<RoughTumble> {

    private static final FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("creature with flying");
    private static final FilterCreaturePermanent filterWithoutFlying = new FilterCreaturePermanent("creature without flying");
    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterWithoutFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public RoughTumble(UUID ownerId) {
        super(ownerId, 118, "Rough", "Tumble", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{R}", "{5}{R}", false);
        this.expansionSetCode = "C13";

        this.color.setRed(true);

        // Rough
        // Rough deals 2 damage to each creature without flying.
        Effect effect = new DamageAllEffect(2, filterWithoutFlying);
        effect.setText("Rough deals 2 damage to each creature without flying");
        getLeftHalfCard().getColor().setRed(true);
        getLeftHalfCard().getSpellAbility().addEffect(effect);

        // Tumble
        // Tumble deals 6 damage to each creature with flying.
        effect = new DamageAllEffect(6, filterFlying);
        effect.setText("Tumble deals 6 damage to each creature with flying");
        getRightHalfCard().getColor().setRed(true);
        getRightHalfCard().getSpellAbility().addEffect(effect);

    }

    public RoughTumble(final RoughTumble card) {
        super(card);
    }

    @Override
    public RoughTumble copy() {
        return new RoughTumble(this);
    }
}
