
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PincherToken;

/**
 *
 * @author Plopman
 */
public final class SummoningStation extends CardImpl {

    public SummoningStation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        // {tap}: Create a 2/2 colorless Pincher creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PincherToken()), new TapSourceCost()));
        // Whenever an artifact is put into a graveyard from the battlefield, you may untap Summoning Station.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new UntapSourceEffect(), true, StaticFilters.FILTER_PERMANENT_ARTIFACT_AN, false));
    }

    private SummoningStation(final SummoningStation card) {
        super(card);
    }

    @Override
    public SummoningStation copy() {
        return new SummoningStation(this);
    }
}
