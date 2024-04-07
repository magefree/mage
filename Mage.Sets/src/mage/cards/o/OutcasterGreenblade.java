package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class OutcasterGreenblade extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic land card or a Desert card");

    static {
        filter.add(Predicates.or(SuperType.BASIC.getPredicate(), SubType.DESERT.getPredicate()));
    }

    private static final DynamicValue xValue =
            new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.DESERT));

    public OutcasterGreenblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Outcaster Greenblade enters the battlefield, search your library for a basic land card or a Desert card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // Outcaster Greenblade gets +1/+1 for each Desert you control.
        this.addAbility(new SimpleStaticAbility(
                new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)
                        .setText("{this} gets +1/+1 for each Desert you control")
        ));
    }

    private OutcasterGreenblade(final OutcasterGreenblade card) {
        super(card);
    }

    @Override
    public OutcasterGreenblade copy() {
        return new OutcasterGreenblade(this);
    }
}
