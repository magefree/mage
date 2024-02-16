
package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 *
 * @author TheElk801
 */
public final class ThranTemporalGateway extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("a historic permanent card");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public ThranTemporalGateway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);

        // {4}, {t}: You may put a historic permanent card from your hand onto the battlefield. (Artifacts, legendaries, and Sagas are historic.)
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(filter)
                        .setText("You may put a historic permanent card from your hand onto the battlefield. "
                                + "<i>(Artifacts, legendaries, and Sagas are historic.)</i>"),
                new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ThranTemporalGateway(final ThranTemporalGateway card) {
        super(card);
    }

    @Override
    public ThranTemporalGateway copy() {
        return new ThranTemporalGateway(this);
    }
}
