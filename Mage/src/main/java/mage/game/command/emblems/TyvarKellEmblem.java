package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.command.Emblem;

/**
 * @author weirddan455
 */
public final class TyvarKellEmblem extends Emblem {

    private static final FilterSpell filter = new FilterSpell("an Elf spell");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    // −6: You get an emblem with "Whenever you cast an Elf spell, it gains haste until end of turn and you draw two cards."
    public TyvarKellEmblem() {
        super("Emblem Tyvar");

        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.COMMAND,
                new GainAbilityTargetEffect(
                        HasteAbility.getInstance(), Duration.EndOfTurn, null, true
                ).setText("it gains haste until end of turn"), filter, false, true, true
        );
        ability.addEffect(new DrawCardSourceControllerEffect(2, "you").concatBy("and"));
        this.getAbilities().add(ability);
    }

    private TyvarKellEmblem(final TyvarKellEmblem card) {
        super(card);
    }

    @Override
    public TyvarKellEmblem copy() {
        return new TyvarKellEmblem(this);
    }
}
