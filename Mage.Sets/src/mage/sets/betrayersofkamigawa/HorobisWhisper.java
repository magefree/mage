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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class HorobisWhisper extends CardImpl<HorobisWhisper> {

    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("nonblack creature");
    private static final FilterLandPermanent filterCondition = new FilterLandPermanent("Swamp");

    static {
        filterTarget.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filterCondition.add(new SubtypePredicate("Swamp"));
    }

    public HorobisWhisper(UUID ownerId) {
        super(ownerId, 70, "Horobi's Whisper", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");

        this.color.setBlack(true);


        // If you control a Swamp, destroy target nonblack creature.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DestroyTargetEffect(), 
                new ControlsPermanentCondition(filterCondition),"If you control a Swamp, destroy target nonblack creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterTarget) );

        // Splice onto Arcane-Exile four cards from your graveyard.
        this.addAbility(new SpliceOntoArcaneAbility(new ExileFromGraveCost(new TargetCardInYourGraveyard(4,4, new FilterCard("cards")))));
        

    }

    public HorobisWhisper(final HorobisWhisper card) {
        super(card);
    }

    @Override
    public HorobisWhisper copy() {
        return new HorobisWhisper(this);
    }
}
