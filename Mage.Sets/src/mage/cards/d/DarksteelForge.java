package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class DarksteelForge extends CardImpl {

    public DarksteelForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{9}");

        // Artifacts you control are indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(IndestructibleAbility.getInstance(),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS, false)));
    }

    private DarksteelForge(final DarksteelForge card) {
        super(card);
    }

    @Override
    public DarksteelForge copy() {
        return new DarksteelForge(this);
    }

}
