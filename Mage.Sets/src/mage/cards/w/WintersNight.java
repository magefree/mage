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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddManaOfAnyTypeProducedEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class WintersNight extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent("a player taps a snow land");
    {
        filter.add(new SupertypePredicate(SuperType.SNOW));
    }

    public WintersNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}{G}{W}");
        addSuperType(SuperType.WORLD);

        // Whenever a player taps a snow land for mana, that player adds one mana to their mana pool of any type that land produced.
        // That land doesn't untap during its controller's next untap step.
        ManaEffect effect = new AddManaOfAnyTypeProducedEffect();
        effect.setText("that player adds one mana to their mana pool of any type that land produced");
        Ability ability = new TapForManaAllTriggeredManaAbility(effect, filter, SetTargetPointer.PERMANENT);
        Effect effect2 = new DontUntapInControllersNextUntapStepTargetEffect();
        effect2.setText("That land doesn't untap during its controller's next untap step");
        ability.addEffect(effect2);
        this.addAbility(ability);
    }

    public WintersNight(final WintersNight card) {
        super(card);
    }

    @Override
    public WintersNight copy() {
        return new WintersNight(this);
    }
}
