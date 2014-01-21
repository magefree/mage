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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasSubtypeCondition;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class ErebossEmissary extends CardImpl<ErebossEmissary> {

    public ErebossEmissary(UUID ownerId) {
        super(ownerId, 86, "Erebos's Emissary", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "THS";
        this.subtype.add("Snake");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bestow {5}{B}
        this.addAbility(new BestowAbility(this, "{5}{B}"));
        // Discard a creature card: Erebos's Emissary gets +2/+2 until end of turn. If Erebos's Emissary is an Aura, enchanted creature gets +2/+2 until end of turn instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(
                new BoostEnchantedEffect(2,2, Duration.EndOfTurn),
                new BoostSourceEffect(2,2, Duration.EndOfTurn),
                new SourceHasSubtypeCondition("Aura"),
                "{this} gets +2/+2 until end of turn. If Erebos's Emissary is an Aura, enchanted creature gets +2/+2 until end of turn instead"),
                new DiscardTargetCost(new TargetCardInHand(new FilterCreatureCard()))));
        // Enchanted creature gets +3/+3
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3,3, Duration.WhileOnBattlefield)));
    }

    public ErebossEmissary(final ErebossEmissary card) {
        super(card);
    }

    @Override
    public ErebossEmissary copy() {
        return new ErebossEmissary(this);
    }
}
