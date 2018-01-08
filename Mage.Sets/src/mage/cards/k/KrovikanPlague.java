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
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.common.TapAttachedCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.BoostCounter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author L_J
 */
public class KrovikanPlague extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filterNonWall = new FilterControlledCreaturePermanent("non-Wall creature you control");

    static {
        filterNonWall.add(Predicates.not(new SubtypePredicate(SubType.WALL)));
    }

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("enchanted creature is untapped");
    
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public KrovikanPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant non-Wall creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent(filterNonWall);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Krovikan Plague enters the battlefield, draw a card at the beginning of the next turn's upkeep.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse)), false));

        // Tap enchanted creature: Tap enchanted creature: Krovikan Plague deals 1 damage to target creature or player. Put a -0/-1 counter on enchanted creature. Activate this ability only if enchanted creature is untapped.
        Ability ability2 = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(1), new TapAttachedCost(), new AttachedToMatchesFilterCondition(filter));
        ability2.addEffect(new AddCountersAttachedEffect(new BoostCounter(0, -1),"enchanted creature"));
        ability2.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability2);

    }

    public KrovikanPlague(final KrovikanPlague card) {
        super(card);
    }

    @Override
    public KrovikanPlague copy() {
        return new KrovikanPlague(this);
    }
}
