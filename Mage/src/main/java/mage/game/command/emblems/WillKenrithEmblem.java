package mage.game.command.emblems;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class WillKenrithEmblem extends Emblem {
    // Target player gets an emblem with "Whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy."

    public WillKenrithEmblem() {
        super("Emblem Will Kenrith");
        this.getAbilities().add(new SpellCastControllerTriggeredAbility(
                Zone.COMMAND,
                new CopyTargetStackObjectEffect(true).withText("it"),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false,
                SetTargetPointer.SPELL
        ));
    }

    private WillKenrithEmblem(final WillKenrithEmblem card) {
        super(card);
    }

    @Override
    public WillKenrithEmblem copy() {
        return new WillKenrithEmblem(this);
    }
}
