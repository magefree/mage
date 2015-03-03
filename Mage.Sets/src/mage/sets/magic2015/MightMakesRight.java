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
package mage.sets.magic2015;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class MightMakesRight extends CardImpl {

    private static final String ruleText = "At the beginning of combat on your turn, if you control each creature on the battlefield with the greatest power, "
            + "gain control of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.";
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
    
    public MightMakesRight(UUID ownerId) {
        super(ownerId, 156, "Might Makes Right", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");
        this.expansionSetCode = "M15";

        this.color.setRed(true);

        // At the beginning of combat on your turn, if you control each creature on the battlefield with the greatest power, gain control
        // of target creature an opponent controls until end of turn. Untap that creature. It gains haste until end of turn.
        TriggeredAbility gainControlAbility = new BeginningOfCombatTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn), TargetController.YOU, false);
        gainControlAbility.addEffect(new UntapTargetEffect());
        gainControlAbility.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        gainControlAbility.addTarget(new TargetCreaturePermanent(filter));
        Ability conditionalAbility = new ConditionalTriggeredAbility(gainControlAbility, ControlsEachCreatureWithGreatestPowerCondition.getInstance(), ruleText);
        this.addAbility(conditionalAbility);
    }

    public MightMakesRight(final MightMakesRight card) {
        super(card);
    }

    @Override
    public MightMakesRight copy() {
        return new MightMakesRight(this);
    }
}

class ControlsEachCreatureWithGreatestPowerCondition implements Condition {

    private static final ControlsEachCreatureWithGreatestPowerCondition fInstance = new ControlsEachCreatureWithGreatestPowerCondition();
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public static Condition getInstance() {
        return fInstance;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Integer maxPower = null;
        boolean result = false;
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game);
        for (Permanent permanent : permanents) {
            if (permanent == null) {
                continue;
            }
            int power = permanent.getPower().getValue();
            if (maxPower == null || power > maxPower) {
                maxPower = permanent.getPower().getValue();
                result = true;
            }
            if (power == maxPower) {
                result &= permanent.getControllerId().equals(source.getControllerId());
            }
        }
        return result;
    }
    
}
