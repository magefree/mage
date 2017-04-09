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
package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class YomijiWhoBarsTheWay extends CardImpl {

    public YomijiWhoBarsTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Spirit");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a legendary permanent other than Yomiji, Who Bars the Way is put into a graveyard from the battlefield, return that card to its owner's hand.
        FilterPermanent filter = new FilterPermanent("a legendary permanent other than " + getName());
        filter.add(new AnotherPredicate());
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return that card to its owner's hand");
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(effect, false, filter, true));
    }

    public YomijiWhoBarsTheWay(final YomijiWhoBarsTheWay card) {
        super(card);
    }

    @Override
    public YomijiWhoBarsTheWay copy() {
        return new YomijiWhoBarsTheWay(this);
    }
}
