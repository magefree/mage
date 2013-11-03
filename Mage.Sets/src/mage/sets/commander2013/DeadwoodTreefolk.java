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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldOrDiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherCardPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class DeadwoodTreefolk extends CardImpl<DeadwoodTreefolk> {

    private static final FilterCreatureCard filter = new FilterCreatureCard("another creature card from your graveyard");
    static {
        filter.add(new AnotherCardPredicate());
    }

    public DeadwoodTreefolk(UUID ownerId) {
        super(ownerId, 141, "Deadwood Treefolk", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{G}");
        this.expansionSetCode = "C13";
        this.subtype.add("Treefolk");

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Vanishing 3
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(3)));
        ability.setRuleVisible(false);
        this.addAbility(ability);
        this.addAbility(new VanishingUpkeepAbility(3));
        this.addAbility(new VanishingSacrificeAbility());
        // When Deadwood Treefolk enters the battlefield or leaves the battlefield, return another target creature card from your graveyard to your hand.
        ability = new EntersBattlefieldOrDiesSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        Target target = new TargetCardInYourGraveyard(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public DeadwoodTreefolk(final DeadwoodTreefolk card) {
        super(card);
    }

    @Override
    public DeadwoodTreefolk copy() {
        return new DeadwoodTreefolk(this);
    }
}
