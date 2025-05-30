package mage.cards.o;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.SacrificeAllControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OverlaidTerrain extends CardImpl {

    public OverlaidTerrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // As Overlaid Terrain enters the battlefield, sacrifice all lands you control.
        this.addAbility(new AsEntersBattlefieldAbility(new SacrificeAllControllerEffect(StaticFilters.FILTER_LANDS)));

        // Lands you control have "{T}: Add two mana of any one color."
        SimpleManaAbility manaAbility = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(manaAbility, Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS, false)));
    }

    private OverlaidTerrain(final OverlaidTerrain card) {
        super(card);
    }

    @Override
    public OverlaidTerrain copy() {
        return new OverlaidTerrain(this);
    }
}