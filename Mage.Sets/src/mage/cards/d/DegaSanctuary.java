
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SanctuaryInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class DegaSanctuary extends CardImpl {

    public DegaSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // At the beginning of your upkeep, if you control a black or red permanent, you gain 2 life. If you control a black permanent and a red permanent, you gain 4 life instead.
        Ability ability = new SanctuaryInterveningIfTriggeredAbility(
                new GainLifeEffect(2), new GainLifeEffect(4), ObjectColor.BLACK, ObjectColor.RED,
                "At the beginning of your upkeep, if you control a black or red permanent, you gain 2 life. "
                + "If you control a black permanent and a red permanent, you gain 4 life instead."
        );
        this.addAbility(ability);
    }

    private DegaSanctuary(final DegaSanctuary card) {
        super(card);
    }

    @Override
    public DegaSanctuary copy() {
        return new DegaSanctuary(this);
    }
}
