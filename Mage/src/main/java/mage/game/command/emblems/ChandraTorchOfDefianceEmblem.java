package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.common.TargetAnyTarget;

/**
 * @author spjspj
 */
public final class ChandraTorchOfDefianceEmblem extends Emblem {

    // You get an emblem with "Whenever you cast a spell, this emblem deals 5 damage to any target."
    public ChandraTorchOfDefianceEmblem() {
        super("Emblem Chandra");
        Effect effect = new DamageTargetEffect(5);
        effect.setText("this emblem deals 5 damage to any target");
        Ability ability = new SpellCastControllerTriggeredAbility(Zone.COMMAND, effect, StaticFilters.FILTER_SPELL_A, false, false);
        ability.addTarget(new TargetAnyTarget());
        getAbilities().add(ability);
    }

    private ChandraTorchOfDefianceEmblem(final ChandraTorchOfDefianceEmblem card) {
        super(card);
    }

    @Override
    public ChandraTorchOfDefianceEmblem copy() {
        return new ChandraTorchOfDefianceEmblem(this);
    }
}
