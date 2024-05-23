package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class DeathPitOffering extends CardImpl {

    public DeathPitOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // When Death Pit Offering enters the battlefield, sacrifice all creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURES)));
        // Creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false)));
    }

    private DeathPitOffering(final DeathPitOffering card) {
        super(card);
    }

    @Override
    public DeathPitOffering copy() {
        return new DeathPitOffering(this);
    }
}