
package mage.abilities.effects.common.continuous;

import java.util.HashSet;
import java.util.Set;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.choices.ChoiceImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

/**
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

    protected LoseAbilityOrAnotherAbilityTargetEffect(final LoseAbilityOrAnotherAbilityTargetEffect effect) {
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
            chooseAbility.setMessage("Choose an ability to remove");

            Set<String> choice = new HashSet<>();

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
                    permanent.removeAbility(ability, source.getSourceId(), game);
                } else if (chosenAbility.equals(ability2.getRule())) {
                    permanent.removeAbility(ability2, source.getSourceId(), game);
                }
            } else {
                return false;
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
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
