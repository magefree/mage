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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author anonymous
 */
public class DormantSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Sliver", "All Sliver creatures");

    private static final FilterPermanent filterSlivers = new FilterPermanent("Sliver", "All Slivers");

    public DormantSliver(UUID ownerId) {
        super(ownerId, 156, "Dormant Sliver", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Sliver");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Sliver creatures have defender.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(DefenderAbility.getInstance(), Duration.WhileOnBattlefield, filter, "All Sliver creatures have defender.")));
        // All Slivers have "When this permanent enters the battlefield, draw a card."
        Ability ability2 = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability2, Duration.WhileOnBattlefield, filter, "All Slivers have \"When this permanent enters the battlefield, draw a card.\"")));
    }

    public DormantSliver(final DormantSliver card) {
        super(card);
    }

    @Override
    public DormantSliver copy() {
        return new DormantSliver(this);
    }
}
