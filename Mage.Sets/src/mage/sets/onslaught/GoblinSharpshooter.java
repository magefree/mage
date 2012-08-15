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
package mage.sets.onslaught;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SkipUntapSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jonubuu
 */
public class GoblinSharpshooter extends CardImpl<GoblinSharpshooter> {

    public GoblinSharpshooter(UUID ownerId) {
        super(ownerId, 207, "Goblin Sharpshooter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "ONS";
        this.subtype.add("Goblin");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Goblin Sharpshooter doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipUntapSourceEffect()));
        // Whenever a creature dies, untap Goblin Sharpshooter.
        this.addAbility(new DiesCreatureTriggeredAbility(new UntapSourceEffect(), true));
        // {tap}: Goblin Sharpshooter deals 1 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public GoblinSharpshooter(final GoblinSharpshooter card) {
        super(card);
    }

    @Override
    public GoblinSharpshooter copy() {
        return new GoblinSharpshooter(this);
    }
}
