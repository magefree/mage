package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class AjanisWelcome extends CardImpl {

    public AjanisWelcome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // Whenever a creature enters the battlefield under your control, you gain 1 life.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new GainLifeEffect(1),
                StaticFilters.FILTER_PERMANENT_A_CREATURE
        ));
    }

    private AjanisWelcome(final AjanisWelcome card) {
        super(card);
    }

    @Override
    public AjanisWelcome copy() {
        return new AjanisWelcome(this);
    }
}
