
package mage.game.command.emblems;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 *
 * @author TheElk801
 */
public final class WillKenrithEmblem extends Emblem {
    // Target player gets an emblem with "Whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy."

    public WillKenrithEmblem() {
        this.setName("Emblem Will Kenrith");
        this.getAbilities().add(new SpellCastControllerTriggeredAbility(
                Zone.COMMAND,
                new CopyTargetSpellEffect(true)
                        .setText("copy that spell. You may choose new targets for the copy"),
                StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY,
                false,
                true
        ));
    }
}
