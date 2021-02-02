

package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author Loki
 */
public final class DarksteelForge extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Artifacts you control");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public DarksteelForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{9}");

        // Artifacts you control are indestructible.
        Effect effect = new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, false);
        effect.setText("Artifacts you control are indestructible");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private DarksteelForge(final DarksteelForge card) {
        super(card);
    }

    @Override
    public DarksteelForge copy() {
        return new DarksteelForge(this);
    }

}
