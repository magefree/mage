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
package mage.cards.c;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

import java.util.UUID;

/**
 *
 * @author anonymous
 */
public class CrusadingKnight extends CardImpl {

    private static final FilterLandPermanent swampFilter = new FilterLandPermanent("Swamp your opponent controls");
    static {
      swampFilter.add(new SubtypePredicate(SubType.SWAMP));
      swampFilter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public CrusadingKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Protection from black
      this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // Crusading Knight gets +1/+1 for each Swamp your opponents control.
      PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(swampFilter, 1);
      SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield));
      this.addAbility(ability);
    }

    public CrusadingKnight(final CrusadingKnight card) {
        super(card);
    }

    @Override
    public CrusadingKnight copy() {
        return new CrusadingKnight(this);
    }
}
