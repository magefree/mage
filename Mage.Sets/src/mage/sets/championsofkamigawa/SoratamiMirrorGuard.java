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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UnblockableTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public class SoratamiMirrorGuard extends CardImpl<SoratamiMirrorGuard> {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("a land");
    private final static FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
        filterCreature.add(new PowerPredicate(Filter.ComparisonType.LessThan, 3));
    }

    public SoratamiMirrorGuard(UUID ownerId) {
        super(ownerId, 87, "Soratami Mirror-Guard", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Moonfolk");
        this.subtype.add("Wizard");
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {2}, Return a land you control to its owner's hand: Target creature with power 2 or less is unblockable this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UnblockableTargetEffect(), new GenericManaCost(2));
        ability.addCost(new ReturnToHandTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreaturePermanent(filterCreature));
        this.addAbility(ability);
    }

    public SoratamiMirrorGuard(final SoratamiMirrorGuard card) {
        super(card);
    }

    @Override
    public SoratamiMirrorGuard copy() {
        return new SoratamiMirrorGuard(this);
    }

}
