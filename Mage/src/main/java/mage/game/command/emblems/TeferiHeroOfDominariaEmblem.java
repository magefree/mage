package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.command.Emblem;
import mage.target.TargetPermanent;

import java.util.Arrays;

/**
 *
 * @author LevelX2
 */
public final class TeferiHeroOfDominariaEmblem extends Emblem {

    // Whenever you draw a card, exile target permanent an opponent controls.
    public TeferiHeroOfDominariaEmblem() {
        this.setName("Emblem Teferi");
        Ability ability = new DrawCardControllerTriggeredAbility(Zone.COMMAND, new ExileTargetEffect(), false);
        FilterPermanent filter = new FilterPermanent("permanent an opponent controls");
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        ability.addTarget(new TargetPermanent(filter));
        this.getAbilities().add(ability);

        availableImageSetCodes = Arrays.asList("DOM", "MED");
    }

}
