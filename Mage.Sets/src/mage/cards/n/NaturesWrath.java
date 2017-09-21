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
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author L_J
 */
public class NaturesWrath extends CardImpl {
    private static final FilterPermanent filterBlue = new FilterPermanent("an Island or blue permanent");
    static{
        filterBlue.add(Predicates.or(new ColorPredicate(ObjectColor.BLUE), new SubtypePredicate(SubType.ISLAND)));
    }

    private static final FilterPermanent filterBlack = new FilterPermanent("a Swamp or black permanent");
    static{
        filterBlack.add(Predicates.or(new ColorPredicate(ObjectColor.BLACK), new SubtypePredicate(SubType.SWAMP)));
    }

    public NaturesWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");

        // At the beginning of your upkeep, sacrifice Nature's Wrath unless you pay {G}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{G}")), TargetController.YOU, false));

        // Whenever a player puts an Island or blue permanent onto the battlefield, he or she sacrifices an Island or blue permanent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new SacrificeEffect(filterBlue, 1, ""),
                filterBlue,
                false, SetTargetPointer.PLAYER, 
                "Whenever a player puts an Island or blue permanent onto the battlefield, he or she sacrifices an Island or blue permanent."));

        // Whenever a player puts a Swamp or black permanent onto the battlefield, he or she sacrifices a Swamp or black permanent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new SacrificeEffect(filterBlack, 1, ""),
                filterBlack,
                false, SetTargetPointer.PLAYER, 
                "Whenever a player puts a Swamp or black permanent onto the battlefield, he or she sacrifices a Swamp or black permanent."));
    }

    public NaturesWrath(final NaturesWrath card) {
        super(card);
    }

    @Override
    public NaturesWrath copy() {
        return new NaturesWrath(this);
    }
}
