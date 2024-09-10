package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author fireshoes
 */
public final class LivingPlane extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("all lands");

    public LivingPlane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");
        this.supertype.add(SuperType.WORLD);

        // All lands are 1/1 creatures that are still lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAllEffect(
                new CreatureToken(1, 1, "1/1 creatures"),
                "lands", filter, Duration.WhileOnBattlefield, false)));
    }

    private LivingPlane(final LivingPlane card) {
        super(card);
    }

    @Override
    public LivingPlane copy() {
        return new LivingPlane(this);
    }
}
