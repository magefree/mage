package mage.game.command.emblems;

import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author spjspj
 */
public final class SarkhanTheDragonspeakerEmblem extends Emblem {

    public SarkhanTheDragonspeakerEmblem() {
        super("Emblem Sarkhan");

        this.getAbilities().add(new BeginningOfDrawTriggeredAbility(Zone.COMMAND, new DrawCardSourceControllerEffect(2), TargetController.YOU, false));
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, new DiscardHandControllerEffect(), TargetController.YOU, null, false));
    }

    private SarkhanTheDragonspeakerEmblem(final SarkhanTheDragonspeakerEmblem card) {
        super(card);
    }

    @Override
    public SarkhanTheDragonspeakerEmblem copy() {
        return new SarkhanTheDragonspeakerEmblem(this);
    }
}
