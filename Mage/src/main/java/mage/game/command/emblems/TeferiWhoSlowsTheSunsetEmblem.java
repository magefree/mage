package mage.game.command.emblems;

import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.UntapAllDuringEachOtherPlayersUntapStepEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.command.Emblem;

public class TeferiWhoSlowsTheSunsetEmblem extends Emblem {
    // You get an emblem with "Untap all permanents you control during each opponent's untap step" and "You draw a card during each opponent's draw step."
    public TeferiWhoSlowsTheSunsetEmblem() {
        this.setName("Emblem Teferi");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new UntapAllDuringEachOtherPlayersUntapStepEffect(new FilterControlledPermanent("permanents you control"))));
        this.getAbilities().add(new BeginningOfDrawTriggeredAbility(Zone.COMMAND, new DrawCardSourceControllerEffect(1), TargetController.OPPONENT, false));
    }
}
