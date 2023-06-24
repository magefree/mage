package mage.game.command.emblems;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class RowanScholarOfSparksEmblem extends Emblem {

    // −4: You get an emblem with "Whenever you cast an instant or sorcery spell, you may pay {2}. If you do, copy that spell. You may choose new targets for the copy."
    public RowanScholarOfSparksEmblem() {
        super("Emblem Rowan");
        this.getAbilities().add(new SpellCastControllerTriggeredAbility(
                Zone.COMMAND, new DoIfCostPaid(new CopyTargetSpellEffect(true), new GenericManaCost(2)),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false, true
        ));
    }

    private RowanScholarOfSparksEmblem(final RowanScholarOfSparksEmblem card) {
        super(card);
    }

    @Override
    public RowanScholarOfSparksEmblem copy() {
        return new RowanScholarOfSparksEmblem(this);
    }
}
