
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SanctuaryInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class RakaSanctuary extends CardImpl {

    public RakaSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of your upkeep, if you control a white or blue permanent, Raka Sanctuary deals 1 damage to target creature. If you control a white permanent and a blue permanent, Raka Sanctuary deals 3 damage to that creature instead.
        Ability ability = new SanctuaryInterveningIfTriggeredAbility(
                new DamageTargetEffect(1), new DamageTargetEffect(3), ObjectColor.WHITE, ObjectColor.BLUE,
                "At the beginning of your upkeep, if you control a white or blue permanent, Raka Sanctuary deals 1 damage to target creature. "
                + "If you control a white permanent and a blue permanent, Raka Sanctuary deals 3 damage to that creature instead."
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RakaSanctuary(final RakaSanctuary card) {
        super(card);
    }

    @Override
    public RakaSanctuary copy() {
        return new RakaSanctuary(this);
    }
}
