
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class NavigatorsCompass extends CardImpl {

    public NavigatorsCompass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // When Navigator's Compass enters the battlefield, you gain 3 life.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3));
        this.addAbility(etbAbility);
        // {tap}: Until end of turn, target land you control becomes the basic land type of your choice in addition to its other types.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesBasicLandTargetEffect(Duration.EndOfTurn, true, false)
                .setText("Until end of turn, target land you control becomes the basic land type of your choice in addition to its other types"),
                 new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private NavigatorsCompass(final NavigatorsCompass card) {
        super(card);
    }

    @Override
    public NavigatorsCompass copy() {
        return new NavigatorsCompass(this);
    }
}
