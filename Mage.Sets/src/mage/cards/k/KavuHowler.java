
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author fireshoes
 */
public final class KavuHowler extends CardImpl {

    private static final FilterCard filter = new FilterCard("Kavu cards");

    static {
        filter.add(SubType.KAVU.getPredicate());
    }

    public KavuHowler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Kavu Howler enters the battlefield, reveal the top four cards of your library. Put all Kavu cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new RevealLibraryPutIntoHandEffect(4, filter, Zone.LIBRARY)));
    }

    private KavuHowler(final KavuHowler card) {
        super(card);
    }

    @Override
    public KavuHowler copy() {
        return new KavuHowler(this);
    }
}
