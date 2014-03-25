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
package mage.sets.nemesis;

import java.util.UUID;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class Submerge extends CardImpl<Submerge> {
    
    private static final FilterPermanent filterForest = new FilterPermanent();
    private static final FilterPermanent filterIsland = new FilterPermanent();

    static {
        filterForest.add(new SubtypePredicate(("Forest")));
        filterIsland.add(new SubtypePredicate(("Island")));
    }
    
    public Submerge(UUID ownerId) {
        super(ownerId, 48, "Submerge", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{U}");
        this.expansionSetCode = "NMS";

        this.color.setBlue(true);

        // If an opponent controls a Forest and you control an Island, you may cast Submerge without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Forest and you control an Island", 
                new OpponentControlsPermanentCondition(filterForest),
                new ControlsPermanentCondition(filterIsland));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));        
        // Put target creature on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
                
    }

    public Submerge(final Submerge card) {
        super(card);
    }

    @Override
    public Submerge copy() {
        return new Submerge(this);
    }
}
