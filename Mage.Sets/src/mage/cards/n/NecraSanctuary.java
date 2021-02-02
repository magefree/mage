
package mage.cards.n;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SanctuaryInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author TheElk801
 */
public final class NecraSanctuary extends CardImpl {

    public NecraSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of your upkeep, if you control a green or white permanent, target player loses 1 life. If you control a green permanent and a white permanent, that player loses 3 life instead.
        Ability ability = new SanctuaryInterveningIfTriggeredAbility(
                new LoseLifeTargetEffect(1), new LoseLifeTargetEffect(3), ObjectColor.GREEN, ObjectColor.WHITE,
                "At the beginning of your upkeep, if you control a green or white permanent, "
                + "target player loses 1 life. If you control a green permanent and a white permanent, that player loses 3 life instead."
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private NecraSanctuary(final NecraSanctuary card) {
        super(card);
    }

    @Override
    public NecraSanctuary copy() {
        return new NecraSanctuary(this);
    }
}
