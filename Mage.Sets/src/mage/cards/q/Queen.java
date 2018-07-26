package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author NinthWorld
 */
public final class Queen extends CardImpl {

    private static final FilterCard filter = new FilterCard("Zerg creature cards");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate(SubType.ZERG));
    }

    public Queen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Queen enters the battlefield, reveal the top four cards of your library. Put all Zerg creature cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPutIntoHandEffect(4, filter, Zone.LIBRARY)));
    }

    public Queen(final Queen card) {
        super(card);
    }

    @Override
    public Queen copy() {
        return new Queen(this);
    }
}