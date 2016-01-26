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
package mage.sets.vintagemasters;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class LakeOfTheDead extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Swamp");

    static {
        filter.add(new SubtypePredicate("Swamp"));
    }

    public LakeOfTheDead(UUID ownerId) {
        super(ownerId, 302, "Lake of the Dead", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "VMA";

        // If Lake of the Dead would enter the battlefield, sacrifice a Swamp instead. If you do, put Lake of the Dead onto the battlefield. If you don't, put it into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)))));
        // {tap}: Add {B} to your mana pool.
        this.addAbility(new BlackManaAbility());

        // {tap}, Sacrifice a Swamp: Add {B}{B}{B}{B} to your mana pool.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 0, 0, 4, 0, 0, 0), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

    }

    public LakeOfTheDead(final LakeOfTheDead card) {
        super(card);
    }

    @Override
    public LakeOfTheDead copy() {
        return new LakeOfTheDead(this);
    }
}
