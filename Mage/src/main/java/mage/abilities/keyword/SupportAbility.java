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
package mage.abilities.keyword;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 * 701.32. Support
 *
 * 701.32a “Support N” on a permanent means “Put a +1/+1 counter on each of up
 * to N other target creatures.” “Support N” on an instant or sorcery spell
 * means “Put a +1/+1 counter on each of up to N target creatures.”
 *
 * @author LevelX2
 */
public class SupportAbility extends EntersBattlefieldTriggeredAbility {

    public SupportAbility(Card card, int amount) {
        super(new SupportEffect(card, amount, true));
        if (!card.getCardType().contains(CardType.INSTANT) && !card.getCardType().contains(CardType.SORCERY)) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures");
            if (card.getCardType().contains(CardType.CREATURE)) {
                filter.add(new AnotherPredicate());
                filter.setMessage("other target creatures");
            }
            addTarget(new TargetCreaturePermanent(0, amount, filter, false));
        }
    }

    public SupportAbility(final SupportAbility ability) {
        super(ability);
    }

    @Override
    public SupportAbility copy() {
        return new SupportAbility(this);
    }
}
