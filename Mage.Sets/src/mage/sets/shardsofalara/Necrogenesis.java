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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public class Necrogenesis extends CardImpl<Necrogenesis> {

    public Necrogenesis(UUID ownerId) {
        super(ownerId, 181, "Necrogenesis", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}{G}");
        this.expansionSetCode = "ALA";

        this.color.setGreen(true);
        this.color.setBlack(true);

        // {2}: Exile target creature card from a graveyard. Put a 1/1 green Saproling creature token onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard()));
        ability.addEffect(new CreateTokenEffect(new SaprolingToken()));
        this.addAbility(ability);
    }

    public Necrogenesis(final Necrogenesis card) {
        super(card);
    }

    @Override
    public Necrogenesis copy() {
        return new Necrogenesis(this);
    }
}
