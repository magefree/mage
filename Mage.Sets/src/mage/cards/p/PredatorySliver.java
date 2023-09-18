
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class PredatorySliver extends CardImpl {

    public PredatorySliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sliver creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(1, 1, Duration.WhileInGraveyard, StaticFilters.FILTER_PERMANENT_SLIVERS)));
    }

    private PredatorySliver(final PredatorySliver card) {
        super(card);
    }

    @Override
    public PredatorySliver copy() {
        return new PredatorySliver(this);
    }
}
