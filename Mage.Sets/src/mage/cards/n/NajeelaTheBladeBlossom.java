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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.WarriorToken;

/**
 *
 * @author TheElk801
 */
public final class NajeelaTheBladeBlossom extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.WARRIOR, "a Warrior");

    public NajeelaTheBladeBlossom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a Warrior attacks, you may have its controller create a 1/1 white Warrior creature token that's tapped and attacking.
        this.addAbility(new AttacksAllTriggeredAbility(
                new CreateTokenTargetEffect(new WarriorToken(), new StaticValue(1), true, true)
                        .setText("you may have its controller create a 1/1 white Warrior creature token that's tapped and attacking"),
                true, filter, SetTargetPointer.PLAYER, false, true
        ));

        // {W}{U}{B}{R}{G}: Untap all attacking creatures. They gain trample, lifelink, and haste until end of turn. After this phase, there is an additional combat phase. Activate this ability only during combat.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new UntapAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES),
                new ManaCostsImpl("{W}{U}{B}{R}{G}"),
                new IsPhaseCondition(TurnPhase.COMBAT)
        );
        ability.addEffect(new GainAbilityAllEffect(
                TrampleAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).setText("They gain trample,"));
        ability.addEffect(new GainAbilityAllEffect(
                LifelinkAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).setText("lifelink,"));
        ability.addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).setText("and haste until end of turn."));
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    public NajeelaTheBladeBlossom(final NajeelaTheBladeBlossom card) {
        super(card);
    }

    @Override
    public NajeelaTheBladeBlossom copy() {
        return new NajeelaTheBladeBlossom(this);
    }
}
