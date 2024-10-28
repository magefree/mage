package mage.game.command.emblems;

import mage.abilities.triggers.BeginningOfDrawTriggeredAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
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

        this.getAbilities().add(new BeginningOfDrawTriggeredAbility(Zone.COMMAND, TargetController.YOU, new DrawCardSourceControllerEffect(2).setText("draw two additional cards"), false));
        this.getAbilities().add(new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, TargetController.YOU, new DiscardHandControllerEffect(), false, null));
    }

    private SarkhanTheDragonspeakerEmblem(final SarkhanTheDragonspeakerEmblem card) {
        super(card);
    }

    @Override
    public SarkhanTheDragonspeakerEmblem copy() {
        return new SarkhanTheDragonspeakerEmblem(this);
    }
}
