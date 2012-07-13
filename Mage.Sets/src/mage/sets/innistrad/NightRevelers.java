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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;

import java.util.Set;
import java.util.UUID;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author North
 */
public class NightRevelers extends CardImpl<NightRevelers> {

    private static final String rule = "{this} has haste as long as an opponent controls a Human.";

    public NightRevelers(UUID ownerId) {
        super(ownerId, 153, "Night Revelers", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Vampire");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Night Revelers has haste as long as an opponent controls a Human.
        ConditionalContinousEffect effect = new ConditionalContinousEffect(new GainAbilitySourceEffect(HasteAbility.getInstance()), new NightRevelersCondition(), rule);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, effect));

    }

    public NightRevelers(final NightRevelers card) {
        super(card);
    }

    @Override
    public NightRevelers copy() {
        return new NightRevelers(this);
    }
}

class NightRevelersCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        FilterPermanent filter = new FilterPermanent();
        filter.add(new SubtypePredicate("Human"));

        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        for (UUID opponentId : opponents) {
            conditionApplies |= game.getBattlefield().countAll(filter, opponentId, game) > 0;
        }
        return conditionApplies;
    }
}