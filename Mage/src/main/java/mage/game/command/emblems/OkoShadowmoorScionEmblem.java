package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class OkoShadowmoorScionEmblem extends Emblem {

    // -6: Choose a creature type. You get an emblem with "Creatures you control of the chosen type get +3/+3 and have vigilance and hexproof."
    public OkoShadowmoorScionEmblem() {
        this((SubType) null);
    }

    public OkoShadowmoorScionEmblem(SubType subType) {
        super("Emblem Oko");
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
        if (subType != null) {
            filter.add(subType.getPredicate());
        }
        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new BoostAllEffect(
                        3, 3, Duration.WhileOnBattlefield, filter, false
                ).setText("creatures you control of the chosen type get +3/+3")
        );
        ability.addEffect(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield,
                filter, "and have vigilance"
        ));
        ability.addEffect(new GainAbilityAllEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield,
                filter, "and hexproof"
        ));
        if (subType != null) {
            ability.addEffect(new InfoEffect("<i>(Chosen subtype: " + subType + ")</i>"));
        }
        this.getAbilities().add(ability);
    }

    private OkoShadowmoorScionEmblem(final OkoShadowmoorScionEmblem card) {
        super(card);
    }

    @Override
    public OkoShadowmoorScionEmblem copy() {
        return new OkoShadowmoorScionEmblem(this);
    }
}
