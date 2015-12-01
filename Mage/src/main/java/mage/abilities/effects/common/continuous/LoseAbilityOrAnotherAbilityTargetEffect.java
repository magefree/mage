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
package mage.abilities.effects.common.continuous;

import java.util.HashSet;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.choices.ChoiceImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

/**
 *
 * @author a
 */
public class LoseAbilityOrAnotherAbilityTargetEffect extends LoseAbilityTargetEffect {

    protected Ability ability2;

    public LoseAbilityOrAnotherAbilityTargetEffect(Ability ability, Ability ability2) {
        this(ability, ability2, Duration.WhileOnBattlefield);
    }

    public LoseAbilityOrAnotherAbilityTargetEffect(Ability ability, Ability ability2, Duration duration) {
        super(ability, duration);
        this.ability2 = ability2;
    }

    public LoseAbilityOrAnotherAbilityTargetEffect(final LoseAbilityOrAnotherAbilityTargetEffect effect) {
        super(effect);
        this.ability2 = effect.ability2.copy();
    }

    @Override
    public LoseAbilityOrAnotherAbilityTargetEffect copy() {
        return new LoseAbilityOrAnotherAbilityTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            ChoiceImpl chooseAbility = new ChoiceImpl();
            chooseAbility.setMessage("What ability do you wish to remove?");

            HashSet<String> choice = new HashSet<>();

            if (permanent.getAbilities().contains(ability)) {
                choice.add(ability.getRule());
            }

            if (permanent.getAbilities().contains(ability2)) {
                choice.add(ability2.getRule());
            }

            chooseAbility.setChoices(choice);

            Player player = game.getPlayer(source.getControllerId());

            if (player.choose(outcome, chooseAbility, game)) {
                
                String chosenAbility = chooseAbility.getChoice();
                
                if (chosenAbility.equals(ability.getRule())) {
                    while (permanent.getAbilities().contains(ability)) {
                        permanent.getAbilities().remove(ability);
                    }
                }
                else if (chosenAbility.equals(ability2.getRule())) {
                    while (permanent.getAbilities().contains(ability2)) {
                        permanent.getAbilities().remove(ability2);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        Target target = mode.getTargets().get(0);
        if (target.getNumberOfTargets() > 1) {
            if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                sb.append("Up to");
            }
            sb.append(target.getMaxNumberOfTargets()).append(" target ").append(target.getTargetName()).append(" loses ");
        } else {
            sb.append("Target ").append(target.getTargetName()).append(" loses ");
        }
        sb.append(ability.getRule());
        sb.append(" or ");
        sb.append(ability2.getRule());
        if (!duration.toString().isEmpty()) {
            sb.append(" ").append(duration.toString());
        }
        return sb.toString();
    }
}
