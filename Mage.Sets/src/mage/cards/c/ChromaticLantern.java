
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author LevelX2
 */
public final class ChromaticLantern extends CardImpl {

    public ChromaticLantern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Lands you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new AnyColorManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS, false)));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

    }

    private ChromaticLantern(final ChromaticLantern card) {
        super(card);
    }

    @Override
    public ChromaticLantern copy() {
        return new ChromaticLantern(this);
    }
}
