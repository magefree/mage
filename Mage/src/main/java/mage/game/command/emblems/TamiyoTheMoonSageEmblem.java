package mage.game.command.emblems;

import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class TamiyoTheMoonSageEmblem extends Emblem {

    /**
     * Emblem with "You have no maximum hand size" and "Whenever a card is put
     * into your graveyard from anywhere, you may return it to your hand."
     */

    public TamiyoTheMoonSageEmblem() {
        this.setName("Emblem Tamiyo");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.Custom, HandSizeModification.SET
        )));
        this.getAbilities().add(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                Zone.COMMAND, new ReturnToHandTargetEffect().setText("return it to your hand"),
                true, StaticFilters.FILTER_CARD_A, TargetController.YOU, SetTargetPointer.CARD
        ));
    }
}
