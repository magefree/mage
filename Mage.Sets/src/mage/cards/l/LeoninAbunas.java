
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class LeoninAbunas extends CardImpl {

    public LeoninAbunas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Artifacts you control have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ARTIFACTS, false)));
    }

    private LeoninAbunas(final LeoninAbunas card) {
        super(card);
    }

    @Override
    public LeoninAbunas copy() {
        return new LeoninAbunas(this);
    }
}
