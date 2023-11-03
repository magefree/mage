package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaslemsStonetree extends CardImpl {

    public KaslemsStonetree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");
        this.secondSideCardClazz = mage.cards.k.KaslemsStrider.class;

        // When Kaslem's Stonetree enters the battlefield, look at the top six cards of your library. You may put a land card from among them onto the battlefield tapped. Put the rest on the bottom in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, StaticFilters.FILTER_CARD_LAND_A,
                PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM
        )));

        // Craft with Cave {5}{G}
        this.addAbility(new CraftAbility(
                "{5}{G}", "Cave", "another Cave you " +
                "control or a Cave card in your graveyard", SubType.CAVE.getPredicate()
        ));
    }

    private KaslemsStonetree(final KaslemsStonetree card) {
        super(card);
    }

    @Override
    public KaslemsStonetree copy() {
        return new KaslemsStonetree(this);
    }
}
