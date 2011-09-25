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
package mage.sets.dissension;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public class RakdosCarnarium extends CardImpl<RakdosCarnarium> {
    private final static FilterControlledPermanent filter = new FilterControlledPermanent("land you control");

    static {
        filter.getCardType().add(CardType.LAND);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }

    public RakdosCarnarium(UUID ownerId) {
        super(ownerId, 178, "Rakdos Carnarium", Rarity.COMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "DIS";

        // Rakdos Carnarium enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // When Rakdos Carnarium enters the battlefield, return a land you control to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        Target target = new TargetControlledPermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
        // {tap}: Add {B}{R} to your mana pool.
        this.addAbility(new SimpleManaAbility(Constants.Zone.BATTLEFIELD, new BasicManaEffect(new Mana(1, 0, 0, 0, 1, 0, 0)), new TapSourceCost()));
    }

    public RakdosCarnarium(final RakdosCarnarium card) {
        super(card);
    }

    @Override
    public RakdosCarnarium copy() {
        return new RakdosCarnarium(this);
    }
}
