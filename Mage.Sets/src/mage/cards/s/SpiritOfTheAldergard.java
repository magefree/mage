package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiritOfTheAldergard extends CardImpl {

    private static final FilterCard filter
            = new FilterLandCard("snow land card");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent("other snow permanent you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter2.add(SuperType.SNOW.getPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter2);

    public SpiritOfTheAldergard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // When Spirit of the Aldergard enters the battlefield, search your library for a snow land card, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // Spirit of the Aldergard gets +1/+0 for each other snow permanent you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.WhileOnBattlefield
        )));
    }

    private SpiritOfTheAldergard(final SpiritOfTheAldergard card) {
        super(card);
    }

    @Override
    public SpiritOfTheAldergard copy() {
        return new SpiritOfTheAldergard(this);
    }
}
