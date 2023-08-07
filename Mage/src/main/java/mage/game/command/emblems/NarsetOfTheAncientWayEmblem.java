package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class NarsetOfTheAncientWayEmblem extends Emblem {

    // −6: You get an emblem with "Whenever you cast a noncreature spell, this emblem deals 2 damage to any target."
    public NarsetOfTheAncientWayEmblem() {
        super("Emblem Narset");
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.COMMAND, new DamageTargetEffect(2, "this emblem"),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false, SetTargetPointer.NONE
        );
        ability.addTarget(new TargetAnyTarget());
        this.getAbilities().add(ability);
    }

    private NarsetOfTheAncientWayEmblem(final NarsetOfTheAncientWayEmblem card) {
        super(card);
    }

    @Override
    public NarsetOfTheAncientWayEmblem copy() {
        return new NarsetOfTheAncientWayEmblem(this);
    }
}
