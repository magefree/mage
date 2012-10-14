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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class Anathemancer extends CardImpl<Anathemancer> {

    public Anathemancer(UUID ownerId) {
        super(ownerId, 33, "Anathemancer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Zombie");
        this.subtype.add("Wizard");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Anathemancer enters the battlefield, it deals damage to target player equal to the number of nonbasic lands that player controls.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(new AnathemancerCount()));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Unearth {5}{B}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl("{5}{B}{R}")));
    }

    public Anathemancer(final Anathemancer card) {
        super(card);
    }

    @Override
    public Anathemancer copy() {
        return new Anathemancer(this);
    }
}

class AnathemancerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (sourceAbility.getFirstTarget() == null) {
            return 0;
        }

        FilterLandPermanent filter = new FilterLandPermanent();
        filter.add(Predicates.not(new SupertypePredicate("Basic")));
        filter.add(new ControllerIdPredicate(sourceAbility.getFirstTarget()));

        return game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
    }

    @Override
    public DynamicValue clone() {
        return new AnathemancerCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "nonbasic lands that player controls";
    }
}
