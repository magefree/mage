package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
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
public final class BeastriderVanguard extends CardImpl {

    public BeastriderVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{G}: Look at the top three cards of your library. You may reveal a permanent card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                3, 1, StaticFilters.FILTER_CARD_A_PERMANENT, PutCards.HAND, PutCards.BOTTOM_ANY
        ), new ManaCostsImpl<>("{4}{G}")));
    }

    private BeastriderVanguard(final BeastriderVanguard card) {
        super(card);
    }

    @Override
    public BeastriderVanguard copy() {
        return new BeastriderVanguard(this);
    }
}
