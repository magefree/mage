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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class PiousEvangel extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("{this} or another creature");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("another permanent");

    static {
        filter2.add(new AnotherPredicate());
    }

    public PiousEvangel(UUID ownerId) {
        super(ownerId, 34, "Pious Evangel", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Human");
        this.subtype.add("Cleric");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.canTransform = true;
        this.secondSideCard = new WaywardDisciple(ownerId);

        // Whenever Pious Evangel or another creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new GainLifeEffect(1), filter));

        // {2}, {T}, Sacrifice another permanent: Transform Pious Evangel.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(true), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);
    }

    public PiousEvangel(final PiousEvangel card) {
        super(card);
    }

    @Override
    public PiousEvangel copy() {
        return new PiousEvangel(this);
    }
}
