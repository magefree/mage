package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class ChandraSparkHunterEmblem extends Emblem {

    /**
     * Emblem with "Whenever an artifact you control enters, this emblem deals 3 damage to any target."
     */

    public ChandraSparkHunterEmblem() {
        super("Emblem Chandra");
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
                Zone.COMMAND, new DamageTargetEffect(3, "this emblem"),
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.getAbilities().add(ability);
    }

    private ChandraSparkHunterEmblem(final ChandraSparkHunterEmblem card) {
        super(card);
    }

    @Override
    public ChandraSparkHunterEmblem copy() {
        return new ChandraSparkHunterEmblem(this);
    }
}
