package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilhanaWayfinder extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("a creature or land card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)
        ));
    }

    public SilhanaWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Silhana Wayfinder enters the battlefield, look at the top four cards of your library. You may reveal a creature or land card from among them and put it on top of your library. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                new StaticValue(4), false, new StaticValue(1), filter, Zone.LIBRARY, false,
                true, false, Zone.LIBRARY, false, true, false
        ), false));
    }

    private SilhanaWayfinder(final SilhanaWayfinder card) {
        super(card);
    }

    @Override
    public SilhanaWayfinder copy() {
        return new SilhanaWayfinder(this);
    }
}
