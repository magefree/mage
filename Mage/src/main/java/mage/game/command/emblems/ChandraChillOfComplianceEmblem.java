package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author muz
 */
public final class ChandraChillOfComplianceEmblem extends Emblem {

    // You get an emblem with "Whenever you cast a spell, draw a card."
    public ChandraChillOfComplianceEmblem() {
        super("Emblem Chandra");
        Effect effect = new DrawCardSourceControllerEffect(1);
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, effect, StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.NONE);
        getAbilities().add(ability);
    }

    private ChandraChillOfComplianceEmblem(final ChandraChillOfComplianceEmblem card) {
        super(card);
    }

    @Override
    public ChandraChillOfComplianceEmblem copy() {
        return new ChandraChillOfComplianceEmblem(this);
    }
}
