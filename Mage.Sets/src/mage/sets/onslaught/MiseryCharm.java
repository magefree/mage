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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LoneFox
 */
public class MiseryCharm extends CardImpl {

    private static final FilterPermanent filter1 = new FilterPermanent("Cleric");
    private static final FilterCard filter2 = new FilterCard("Cleric card from your graveyard");

    static {
        filter1.add(new SubtypePredicate("Cleric"));
        filter2.add(new SubtypePredicate("Cleric"));
    }

    public MiseryCharm(UUID ownerId) {
        super(ownerId, 158, "Misery Charm", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "ONS";

        // Choose one - Destroy target Cleric
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter1));
        // or return target Cleric card from your graveyard to your hand
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnToHandTargetEffect());
        mode.getTargets().add(new TargetCardInYourGraveyard(filter2));
        this.getSpellAbility().addMode(mode);
        // or target player loses 2 life.
        mode = new Mode();
        mode.getEffects().add(new LoseLifeTargetEffect(2));
        mode.getTargets().add(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    public MiseryCharm(final MiseryCharm card) {
        super(card);
    }

    @Override
    public MiseryCharm copy() {
        return new MiseryCharm(this);
    }
}
