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
package mage.sets.planechase;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public class BorosGarrison extends CardImpl<BorosGarrison> {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("land you control");

    static {
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    public BorosGarrison(UUID ownerId) {
        super(ownerId, 131, "Boros Garrison", Rarity.COMMON, new CardType[]{CardType.LAND}, null);
        this.expansionSetCode = "HOP";
        this.addAbility(new EntersBattlefieldTappedAbility());
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        Target target = new TargetControlledPermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
        this.addAbility(new SimpleManaAbility(Constants.Zone.BATTLEFIELD, new Mana(1, 0, 0, 1, 0, 0, 0), new TapSourceCost()));
    }

    public BorosGarrison(final BorosGarrison card) {
        super(card);
    }

    @Override
    public BorosGarrison copy() {
        return new BorosGarrison(this);
    }
}
