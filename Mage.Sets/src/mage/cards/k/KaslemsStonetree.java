package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaslemsStonetree extends TransformingDoubleFacedCard {

    public KaslemsStonetree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}{G}",
                "Kaslem's Strider",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GOLEM}, "G"
        );

        // Kaslem's Stonetree
        // When Kaslem's Stonetree enters the battlefield, look at the top six cards of your library. You may put a land card from among them onto the battlefield tapped. Put the rest on the bottom in a random order.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, StaticFilters.FILTER_CARD_LAND_A,
                PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM
        )));

        // Craft with Cave {5}{G}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{5}{G}", "Cave", "another Cave you " +
                "control or a Cave card in your graveyard", SubType.CAVE.getPredicate()
        ));

        // Kaslem's Strider
        this.getRightHalfCard().setPT(5, 5);
    }

    private KaslemsStonetree(final KaslemsStonetree card) {
        super(card);
    }

    @Override
    public KaslemsStonetree copy() {
        return new KaslemsStonetree(this);
    }
}
