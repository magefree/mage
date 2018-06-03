
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author fireshoes
 */
public final class CourageousOutrider extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Human card");

    static {
        filter.add(new SubtypePredicate(SubType.HUMAN));
    }

    public CourageousOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Courageous Outrider enters the battlefield, look at the top four cards of your library. You may reveal a Human card from among them
        // and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(new StaticValue(4), false, new StaticValue(1), filter, false) , false));
    }

    public CourageousOutrider(final CourageousOutrider card) {
        super(card);
    }

    @Override
    public CourageousOutrider copy() {
        return new CourageousOutrider(this);
    }
}
