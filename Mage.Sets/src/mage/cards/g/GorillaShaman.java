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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetAdjustment;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public class GorillaShaman extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature artifact with converted mana cost X");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public GorillaShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.APE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //TODO: Make ability properly copiable
        // {X}{X}{1}: Destroy target noncreature artifact with converted mana cost X.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{X}{X}{1}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjustment(TargetAdjustment.X_CMC_EQUAL_PERM);
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getTargetAdjustment() == TargetAdjustment.X_CMC_EQUAL_PERM) {
            int xValue = ability.getManaCostsToPay().getX();
            TargetPermanent oldTarget = (TargetPermanent) ability.getTargets().get(0);
            int minTargets = oldTarget.getMinNumberOfTargets();
            int maxTargets = oldTarget.getMaxNumberOfTargets();
            FilterPermanent filter2 = oldTarget.getFilter().copy();
            filter2.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, xValue));
            ability.getTargets().clear();
            ability.getTargets().add(new TargetPermanent(minTargets, maxTargets, filter2, false));
        }
    }

    public GorillaShaman(final GorillaShaman card) {
        super(card);
    }

    @Override
    public GorillaShaman copy() {
        return new GorillaShaman(this);
    }
}
