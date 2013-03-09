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
package mage.sets.timespiral;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class HarmonicSliver extends CardImpl<HarmonicSliver> {

    private static final FilterCreaturePermanent filterSliver = new FilterCreaturePermanent();
    private static final FilterPermanent filter = new FilterPermanent("artifact or enchantment");
    static {
        filterSliver.add(new SubtypePredicate("Sliver"));
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT)));

    }

    public HarmonicSliver(UUID ownerId) {
        super(ownerId, 240, "Harmonic Sliver", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.expansionSetCode = "TSP";
        this.subtype.add("Sliver");

        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Slivers have "When this permanent enters the battlefield, destroy target artifact or enchantment."
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield,
                filter, "All Slivers have \"When this permanent enters the battlefield, destroy target artifact or enchantment.\"")));
    }

    public HarmonicSliver(final HarmonicSliver card) {
        super(card);
    }

    @Override
    public HarmonicSliver copy() {
        return new HarmonicSliver(this);
    }
}
