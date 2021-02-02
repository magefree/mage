package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class SkallaWolf extends CardImpl {

    private static final FilterCard filter = new FilterCard("a green card");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public SkallaWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Skalla Wolf enters the battlefield, look at the top five cards of your library. You may reveal a green card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(
                        StaticValue.get(5), false, StaticValue.get(1), filter,
                        Zone.LIBRARY, false, true, false, Zone.HAND, true, false, false
                ).setBackInRandomOrder(true).setText("look at the top five cards of your library. "
                        + "You may reveal a green card from among them and put it into your hand. "
                        + "Put the rest on the bottom of your library in a random order.")
        ));
    }

    private SkallaWolf(final SkallaWolf card) {
        super(card);
    }

    @Override
    public SkallaWolf copy() {
        return new SkallaWolf(this);
    }
}
