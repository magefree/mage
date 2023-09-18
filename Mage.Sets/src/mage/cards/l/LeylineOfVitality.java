
package mage.cards.l;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
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

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class LeylineOfVitality extends CardImpl {

    public LeylineOfVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // If Leyline of Vitality is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, false)));

        // Whenever a creature enters the battlefield under your control, you may gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new GainLifeEffect(1),
                StaticFilters.FILTER_PERMANENT_A_CREATURE,
                true)
        );
    }

    private LeylineOfVitality(final LeylineOfVitality card) {
        super(card);
    }

    @Override
    public LeylineOfVitality copy() {
        return new LeylineOfVitality(this);
    }

}
