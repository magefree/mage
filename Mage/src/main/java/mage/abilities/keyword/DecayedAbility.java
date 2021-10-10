package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class DecayedAbility extends StaticAbility {

    public DecayedAbility() {
        super(Zone.BATTLEFIELD, new CantBlockSourceEffect(Duration.WhileOnBattlefield));
        this.addSubAbility(new AttacksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new SacrificeSourceEffect())
        )));
    }

    private DecayedAbility(final DecayedAbility ability) {
        super(ability);
    }

    @Override
    public DecayedAbility copy() {
        return new DecayedAbility(this);
    }

    @Override
    public String getRule() {
        return "decayed";
    }
}
