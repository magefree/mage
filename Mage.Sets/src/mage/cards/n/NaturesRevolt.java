package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author LoneFox
 *
 */
public final class NaturesRevolt extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("all lands");

    public NaturesRevolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}{G}");

        // All lands are 2/2 creatures that are still lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAllEffect(
                new CreatureToken(2, 2, "2/2 creatures"),
                "lands", filter, Duration.WhileOnBattlefield, false)));
    }

    private NaturesRevolt(final NaturesRevolt card) {
        super(card);
    }

    @Override
    public NaturesRevolt copy() {
        return new NaturesRevolt(this);
    }
}
