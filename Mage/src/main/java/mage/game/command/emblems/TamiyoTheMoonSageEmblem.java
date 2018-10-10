
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.command.Emblem;

/**
 *
 * @author spjspj
 */
public final class TamiyoTheMoonSageEmblem extends Emblem {

    /**
     * Emblem with "You have no maximum hand size" and "Whenever a card is put
     * into your graveyard from anywhere, you may return it to your hand."
     */

    public TamiyoTheMoonSageEmblem() {
        this.setName("Emblem Tamiyo");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.EndOfGame, HandSizeModification.SET));
        this.getAbilities().add(ability);
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("return it to your hand");
        this.getAbilities().add(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                Zone.COMMAND, effect, true, new FilterCard("a card"), TargetController.YOU, SetTargetPointer.CARD));
    }
}
