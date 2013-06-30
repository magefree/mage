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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class ThopterFoundry extends CardImpl<ThopterFoundry> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a nontoken artifact");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public ThopterFoundry(UUID ownerId) {
        super(ownerId, 133, "Thopter Foundry", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{W/B}{U}");
        this.expansionSetCode = "ARB";

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setWhite(true);

        // {1}, Sacrifice a nontoken artifact: Put a 1/1 blue Thopter artifact creature token with flying onto the battlefield. You gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterToken()), new GenericManaCost(1));
        ability.addEffect(new GainLifeEffect(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public ThopterFoundry(final ThopterFoundry card) {
        super(card);
    }

    @Override
    public ThopterFoundry copy() {
        return new ThopterFoundry(this);
    }
}

class ThopterToken extends Token {

    ThopterToken() {
        super("Thopter", "a 1/1 blue Thopter artifact creature token with flying");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        color.setBlue(true);
        subtype.add("Thopter");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
}