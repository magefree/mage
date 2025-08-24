package mage.cards.h;

import mage.abilities.common.AttacksPlayerWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SetTargetPointer;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HornOfTheMark extends CardImpl {

    public HornOfTheMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever two or more creatures you control attack a player, look at the top five cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksPlayerWithCreaturesTriggeredAbility(
                new LookLibraryAndPickControllerEffect(5, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                2, StaticFilters.FILTER_CONTROLLED_CREATURES, SetTargetPointer.NONE, false));
    }

    private HornOfTheMark(final HornOfTheMark card) {
        super(card);
    }

    @Override
    public HornOfTheMark copy() {
        return new HornOfTheMark(this);
    }
}
