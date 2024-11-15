package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class NessianGameWarden extends CardImpl {

    private static final DynamicValue xValue2 = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOREST));
    private static final Hint hint = new ValueHint("Forests you control", xValue2);


    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forests you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public NessianGameWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Nessian Game Warden enters the battlefield, look at the top X cards of your library, where X is the number of forests you control.
        // You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                xValue, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_ANY)));
        this.getSpellAbility().addHint(hint);
    }

    private NessianGameWarden(final NessianGameWarden card) {
        super(card);
    }

    @Override
    public NessianGameWarden copy() {
        return new NessianGameWarden(this);
    }
}
