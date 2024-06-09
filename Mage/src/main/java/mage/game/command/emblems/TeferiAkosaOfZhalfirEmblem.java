package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.WardAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class TeferiAkosaOfZhalfirEmblem extends Emblem {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.KNIGHT, "Knights");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.KNIGHT, "");

    // -2: You get an emblem with "Knights you control get +1/+0 and have ward {1}."
    public TeferiAkosaOfZhalfirEmblem() {
        super("Emblem Teferi");
        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter)
        );
        ability.addEffect(new GainAbilityControlledEffect(
                new WardAbility(new GenericManaCost(1), false),
                Duration.WhileOnBattlefield, filter2
        ).setText("and have ward {1}"));
        this.getAbilities().add(ability);
    }

    private TeferiAkosaOfZhalfirEmblem(final TeferiAkosaOfZhalfirEmblem card) {
        super(card);
    }

    @Override
    public TeferiAkosaOfZhalfirEmblem copy() {
        return new TeferiAkosaOfZhalfirEmblem(this);
    }
}
