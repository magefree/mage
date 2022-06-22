
package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class LeoninSunStandard extends CardImpl {

    public LeoninSunStandard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false), new ManaCostsImpl<>("{1}{W}")));
    }

    private LeoninSunStandard(final LeoninSunStandard card) {
        super(card);
    }

    @Override
    public LeoninSunStandard copy() {
        return new LeoninSunStandard(this);
    }
}
