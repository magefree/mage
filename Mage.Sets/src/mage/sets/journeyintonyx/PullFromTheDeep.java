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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public class PullFromTheDeep extends CardImpl<PullFromTheDeep> {

    private static final FilterCard filterInstant = new FilterCard("instant card from your graveyard");
    private static final FilterCard filterSorcery = new FilterCard("sorcery card from your graveyard");

    static {
        filterInstant.add(new CardTypePredicate(CardType.INSTANT));
        filterSorcery.add(new CardTypePredicate(CardType.SORCERY));
    }

    public PullFromTheDeep(UUID ownerId) {
        super(ownerId, 47, "Pull from the Deep", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");
        this.expansionSetCode = "JOU";

        this.color.setBlue(true);

        // Return up to one target instant card and up to one target sorcery card from your graveyard to your hand. Exile Pull from the Deep.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return up to one target instant card");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterInstant));
        effect = new ReturnToHandTargetEffect();
        effect.setText("and up to one target sorcery card from your graveyard to your hand");
        effect.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,1,filterSorcery));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public PullFromTheDeep(final PullFromTheDeep card) {
        super(card);
    }

    @Override
    public PullFromTheDeep copy() {
        return new PullFromTheDeep(this);
    }
}
