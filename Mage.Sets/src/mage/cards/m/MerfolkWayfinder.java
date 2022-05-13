
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author North
 */
public final class MerfolkWayfinder extends CardImpl {

    private static final FilterCard filter = new FilterCard("Island cards");

    static {
        filter.add(SubType.ISLAND.getPredicate());

    }

    public MerfolkWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Merfolk Wayfinder enters the battlefield, reveal the top three cards of your library. Put all Island cards revealed this way into your hand and the rest on the bottom of your library in any order
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPutIntoHandEffect(3, filter, Zone.LIBRARY)));
    }

    private MerfolkWayfinder(final MerfolkWayfinder card) {
        super(card);
    }

    @Override
    public MerfolkWayfinder copy() {
        return new MerfolkWayfinder(this);
    }
}
