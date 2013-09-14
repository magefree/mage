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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LevelX2
 */
public class Nighthowler extends CardImpl<Nighthowler> {

    public Nighthowler(UUID ownerId) {
        super(ownerId, 98, "Nighthowler", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "THS";
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Bestow {2}{B}{B}
        this.addAbility(new BestowAbility(this, "{2}{B}{B}"));
        // Nighthowler and enchanted creature each get +X/+X, where X is the number of creature cards in all graveyards.
        DynamicValue graveCreatures = new CardsInAllGraveyardsCount(new FilterCreatureCard());
        Effect effect = new BoostSourceEffect(graveCreatures, graveCreatures, Duration.WhileOnBattlefield);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new BoostEnchantedEffect(graveCreatures, graveCreatures, Duration.WhileOnBattlefield);
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public Nighthowler(final Nighthowler card) {
        super(card);
    }

    @Override
    public Nighthowler copy() {
        return new Nighthowler(this);
    }
}
