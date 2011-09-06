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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.PreventDamageTargetEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Loki
 */
public class SplitTailMiko extends CardImpl<SplitTailMiko> {

    public SplitTailMiko(UUID ownerId) {
        super(ownerId, 23, "Split-Tail Miko", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Fox");
        this.subtype.add("Cleric");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        // {W}, {tap}: Prevent the next 2 damage that would be dealt to target creature or player this turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new PreventDamageTargetEffect(Constants.Duration.EndOfTurn, 2), new ColoredManaCost(Constants.ColoredManaSymbol.W));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public SplitTailMiko(final SplitTailMiko card) {
        super(card);
    }

    @Override
    public SplitTailMiko copy() {
        return new SplitTailMiko(this);
    }
}
