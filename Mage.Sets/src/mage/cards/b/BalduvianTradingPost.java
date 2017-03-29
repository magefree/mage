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
package mage.cards.b;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class BalduvianTradingPost extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an untapped Mountain");

    static {
        filter.add(new SubtypePredicate("Mountain"));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public BalduvianTradingPost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // If Balduvian Trading Post would enter the battlefield, sacrifice an untapped Mountain instead. If you do, put Balduvian Trading Post onto the battlefield. If you don't, put it into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)))));

        // {tap}: Add {C}{R} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 0, 0, 0, 0, 0, 1), new TapSourceCost()));

        // {1}, {tap}: Balduvian Trading Post deals 1 damage to target attacking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    public BalduvianTradingPost(final BalduvianTradingPost card) {
        super(card);
    }

    @Override
    public BalduvianTradingPost copy() {
        return new BalduvianTradingPost(this);
    }
}
