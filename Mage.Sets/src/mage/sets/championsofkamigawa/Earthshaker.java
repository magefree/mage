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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.target.common.TargetCreatureOrPlayer;

/**
 * @author Loki
 */
public class Earthshaker extends CardImpl<Earthshaker> {

    private final static FilterCard filter = new FilterCard("a Spirit or Arcane spell");

    static {
        filter.getSubtype().add("Spirit");
        filter.getSubtype().add("Arcane");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

    public Earthshaker(UUID ownerId) {
        super(ownerId, 165, "Earthshaker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Spirit");
        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        Ability ability = new SpellCastTriggeredAbility(new DamageTargetEffect(2), filter, false);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public Earthshaker(final Earthshaker card) {
        super(card);
    }

    @Override
    public Earthshaker copy() {
        return new Earthshaker(this);
    }

}
