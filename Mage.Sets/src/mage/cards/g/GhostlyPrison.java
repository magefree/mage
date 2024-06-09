
package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class GhostlyPrison extends CardImpl {

    public GhostlyPrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you
        this.addAbility(new SimpleStaticAbility(
            Zone.BATTLEFIELD,
            new CantAttackYouUnlessPayAllEffect(
                Duration.WhileOnBattlefield,
                new ManaCostsImpl<>("{2}")
            )
        ));

    }

    private GhostlyPrison(final GhostlyPrison card) {
        super(card);
    }

    @Override
    public GhostlyPrison copy() {
        return new GhostlyPrison(this);
    }

}
