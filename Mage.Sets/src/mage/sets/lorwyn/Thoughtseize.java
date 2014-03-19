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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeSourceEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jonubuu
 */
public class Thoughtseize extends CardImpl<Thoughtseize> {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public Thoughtseize(UUID ownerId) {
        super(ownerId, 145, "Thoughtseize", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "LRW";

        this.color.setBlack(true);

        // Target player reveals his or her hand. You choose a nonland card from it. That player discards that card. You lose 2 life.
        this.getSpellAbility().addTarget(new TargetPlayer(true));
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
        this.getSpellAbility().addEffect(new LoseLifeSourceEffect(2));
    }

    public Thoughtseize(final Thoughtseize card) {
        super(card);
    }

    @Override
    public Thoughtseize copy() {
        return new Thoughtseize(this);
    }
}

