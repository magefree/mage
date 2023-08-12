package mage.cards.p;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlanarAtlas extends CardImpl {

    public PlanarAtlas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Planar Atlas enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Planar Atlas enters the battlefield, you may look at the top four cards of your library. If you do, reveal up to one land card from among them, then put that card on top of your library and the rest on the bottom in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_LAND,
                PutCards.TOP_ANY, PutCards.BOTTOM_RANDOM
        ), true));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private PlanarAtlas(final PlanarAtlas card) {
        super(card);
    }

    @Override
    public PlanarAtlas copy() {
        return new PlanarAtlas(this);
    }
}
