
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author emerald000
 */
public final class EnergyFlux extends CardImpl {

    public EnergyFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");


        // All artifacts have "At the beginning of your upkeep, sacrifice this artifact unless you pay {2}."
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD, 
                new GainAbilityAllEffect(
                        new BeginningOfUpkeepTriggeredAbility(
                                new SacrificeSourceUnlessPaysEffect(new GenericManaCost(2)), 
                                TargetController.YOU, 
                                false), 
                        Duration.WhileOnBattlefield, 
                        new FilterArtifactPermanent(), 
                        "All artifacts have \"At the beginning of your upkeep, sacrifice this artifact unless you pay {2}.\"")));
    }

    private EnergyFlux(final EnergyFlux card) {
        super(card);
    }

    @Override
    public EnergyFlux copy() {
        return new EnergyFlux(this);
    }
}
