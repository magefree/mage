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
package mage.sets.phyrexiavsthecoalition;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.TargetPlayer;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author FenrisulfrX
 */
public class ThunderscapeBattlemage extends CardImpl {

    public ThunderscapeBattlemage(UUID ownerId) {
        super(ownerId, 41, "Thunderscape Battlemage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "DDE";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{B} and/or {G}
        KickerAbility kickerAbility = new KickerAbility("{1}{B}");
        kickerAbility.addKickerCost("{G}");
        this.addAbility(kickerAbility);

        // When {this} enters the battlefield, if it was kicked with its {1}{B} kicker, target player discards two cards.
        TriggeredAbility ability1 = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        ability1.addTarget(new TargetPlayer());
        this.addAbility(new ConditionalTriggeredAbility(ability1, new KickedCostCondition("{1}{B}"),
                "When {this} enters the battlefield, if it was kicked with its {1}{B} kicker, target player discards two cards."));

        // When {this} enters the battlefield, if it was kicked with its {G} kicker, destroy target enchantment.
        TriggeredAbility ability2 = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability2.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(new ConditionalTriggeredAbility(ability2, new KickedCostCondition("{G}"),
                "When {this} enters the battlefield, if it was kicked with its {G} kicker, destroy target enchantment."));
    }

    public ThunderscapeBattlemage(final ThunderscapeBattlemage card) {
        super(card);
    }

    @Override
    public ThunderscapeBattlemage copy() {
        return new ThunderscapeBattlemage(this);
    }
}
