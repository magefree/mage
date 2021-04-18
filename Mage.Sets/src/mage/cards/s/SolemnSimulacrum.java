
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class SolemnSimulacrum extends CardImpl {
    public SolemnSimulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true)
                .setText("search your library for a basic land card, put that card onto the battlefield tapped, then shuffle"),
            true));
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
    }

    private SolemnSimulacrum(final SolemnSimulacrum card) {
        super(card);
    }

    @Override
    public SolemnSimulacrum copy() {
        return new SolemnSimulacrum(this);
    }
}
