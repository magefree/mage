package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class VileConsumption extends CardImpl {

    public VileConsumption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}{B}");

        // All creatures have "At the beginning of your upkeep, sacrifice this creature unless you pay 1 life."
        Effect effect = new SacrificeSourceUnlessPaysEffect(new PayLifeCost(1));
        effect.setText("sacrifice this creature unless you pay 1 life");
        Effect effect2 = new GainAbilityAllEffect(new BeginningOfUpkeepTriggeredAbility(effect, TargetController.YOU, false),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_CREATURES);
        this.addAbility(new SimpleStaticAbility(effect2));
    }

    private VileConsumption(final VileConsumption card) {
        super(card);
    }

    @Override
    public VileConsumption copy() {
        return new VileConsumption(this);
    }
}
