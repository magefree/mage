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

import mage.MageInt;
import mage.constants.ComparisonType;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackIfDefenderControlsPermanent;
import mage.abilities.effects.common.combat.CantBlockCreaturesSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 *
 * @author BursegSardaukar
 *
 */
public class GoblinMutant extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature with power 3 or greater");
    static final private FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures with power 3 or greater");

    static {
        filter.add(Predicates.and(new PowerPredicate(ComparisonType.MORE_THAN, 2), Predicates.not(new TappedPredicate())));
        filter2.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public GoblinMutant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add("Goblin");
        this.subtype.add("Mutant");

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        //Trample
        this.addAbility(TrampleAbility.getInstance());

        // Goblin Mutant can't attack if defending player controls an untapped creature with power 3 or greater.
        Effect effect = new CantAttackIfDefenderControlsPermanent(filter);
        effect.setText("{this} can't attack if defending player controls an untapped creature with power 3 or greater.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        //Goblin Mutant can't block creatures with power 3 or greater.
        Effect effectBlock = new CantBlockCreaturesSourceEffect(filter2);
        effectBlock.setText("{this} can't block creatures with power 3 or greater.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effectBlock));
    }

    public GoblinMutant(final GoblinMutant card) {
        super(card);
    }

    @Override
    public GoblinMutant copy() {
        return new GoblinMutant(this);
    }
}
