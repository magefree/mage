package mage.cards.h;

import mage.MageInt;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
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
public final class HouseCartographer extends CardImpl {

    public HouseCartographer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Survival -- At the beginning of your second main phase, if House Cartographer is tapped, reveal cards from the top of your library until you reveal a land card. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new SurvivalAbility(new RevealCardsFromLibraryUntilEffect(
                StaticFilters.FILTER_CARD_LAND_A, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private HouseCartographer(final HouseCartographer card) {
        super(card);
    }

    @Override
    public HouseCartographer copy() {
        return new HouseCartographer(this);
    }
}
