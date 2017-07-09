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
package mage.cards.g;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.common.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class GauntletOfMight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Red creatures");
    private static final FilterLandPermanent filterMountain = new FilterLandPermanent("a Mountain is tapped");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filterMountain.add(new SubtypePredicate(SubType.MOUNTAIN));
    }
    
    public GauntletOfMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Red creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));  
        
        // Whenever a Mountain is tapped for mana, its controller adds {R} to his or her mana pool.
        ManaEffect effect = new AddManaToManaPoolTargetControllerEffect(new Mana(ColoredManaSymbol.R), "his or her");
        effect.setText("its controller adds {R} to his or her mana pool");
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                effect, filterMountain, SetTargetPointer.PLAYER));        
    }

    public GauntletOfMight(final GauntletOfMight card) {
        super(card);
    }

    @Override
    public GauntletOfMight copy() {
        return new GauntletOfMight(this);
    }
}
