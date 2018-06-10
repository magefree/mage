
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.CreatureEntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LeylineOfVitality extends CardImpl {

    public LeylineOfVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        this.addAbility(LeylineAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE, false)));

        this.addAbility(new CreatureEntersBattlefieldTriggeredAbility(new GainLifeEffect(1), true));
    }

    public LeylineOfVitality(final LeylineOfVitality card) {
        super(card);
    }

    @Override
    public LeylineOfVitality copy() {
        return new LeylineOfVitality(this);
    }

}
