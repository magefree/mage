package mage.abilities.abilityword;

import mage.abilities.common.BeginningOfSecondMainTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class SurvivalAbility extends BeginningOfSecondMainTriggeredAbility {

    public SurvivalAbility(Effect effect) {
        this(effect, false);
    }

    public SurvivalAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, TargetController.YOU, optional, false);
        setTriggerPhrase("At the beginning of your second main phase, if {this} is tapped, ");
        setAbilityWord(AbilityWord.SURVIVAL);
    }

    private SurvivalAbility(final SurvivalAbility ability) {
        super(ability);
    }

    @Override
    public SurvivalAbility copy() {
        return new SurvivalAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return Optional
                .ofNullable(getSourcePermanentOrLKI(game))
                .map(Permanent::isTapped)
                .orElse(false);
    }
}
