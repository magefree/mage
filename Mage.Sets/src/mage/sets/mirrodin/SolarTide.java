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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.Filter;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class SolarTide extends CardImpl {
    
    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creatures with power 2 or less");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures with power 3 or greater");
    
    static {
        filter1.add(new PowerPredicate(Filter.ComparisonType.LessThan, 3));
        filter2.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 2));
    }

    public SolarTide(UUID ownerId) {
        super(ownerId, 24, "Solar Tide", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");
        this.expansionSetCode = "MRD";

        // Choose one - Destroy all creatures with power 2 or less;
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter1));
        
        // or destroy all creatures with power 3 or greater.
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyAllEffect(filter2));
        this.getSpellAbility().getModes().addMode(mode);
        
        // Entwine-Sacrifice two lands.
        this.addAbility(new EntwineAbility(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, new FilterControlledLandPermanent("two lands"), true))));
    }

    public SolarTide(final SolarTide card) {
        super(card);
    }

    @Override
    public SolarTide copy() {
        return new SolarTide(this);
    }
}
