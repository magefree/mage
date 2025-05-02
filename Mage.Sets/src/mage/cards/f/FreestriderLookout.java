package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ReachAbility;
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
public final class FreestriderLookout extends CardImpl {

    public FreestriderLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you commit a crime, look at the top five cards of your library. You may put a land card from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order. This ability triggers only once each turn.
        this.addAbility(new CommittedCrimeTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_LAND_A,
                PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM
        )).setTriggersLimitEachTurn(1));
    }

    private FreestriderLookout(final FreestriderLookout card) {
        super(card);
    }

    @Override
    public FreestriderLookout copy() {
        return new FreestriderLookout(this);
    }
}
