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
package mage.sets.legends;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 */
public class IvoryGuardians extends CardImpl {

    private static final FilterCard filter = new FilterCard("red");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    private static final String rule = "Creatures named {this} get +1/+1 as long as an opponent controls a nontoken red permanent";

    public IvoryGuardians(UUID ownerId) {
        super(ownerId, 192, "Ivory Guardians", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.expansionSetCode = "LEG";
        this.subtype.add("Giant");
        this.subtype.add("Cleric");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection from red
        this.addAbility(new ProtectionAbility(filter));

        // Creatures named Ivory Guardians get +1/+1 as long as an opponent controls a nontoken red permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield), new IvoryGuardiansCondition(), rule)));

    }

    public IvoryGuardians(final IvoryGuardians card) {
        super(card);
    }

    @Override
    public IvoryGuardians copy() {
        return new IvoryGuardians(this);
    }
}

class IvoryGuardiansCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        FilterPermanent filter = new FilterPermanent();
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(new ColorPredicate(ObjectColor.RED));
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentId : opponents) {
            conditionApplies |= game.getBattlefield().countAll(filter, opponentId, game) > 0;
        }
        return conditionApplies;
    }
}
