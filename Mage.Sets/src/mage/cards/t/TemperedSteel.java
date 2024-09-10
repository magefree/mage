

package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class TemperedSteel extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Artifact creatures");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public TemperedSteel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, filter, false)));
    }

    private TemperedSteel(final TemperedSteel card) {
        super(card);
    }

    @Override
    public TemperedSteel copy() {
        return new TemperedSteel(this);
    }

}
