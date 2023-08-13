package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KolaghanWarmonger extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public KolaghanWarmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Kolaghan Warmonger attacks, look at the top six cards of your library. You may reveal a Dragon card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private KolaghanWarmonger(final KolaghanWarmonger card) {
        super(card);
    }

    @Override
    public KolaghanWarmonger copy() {
        return new KolaghanWarmonger(this);
    }
}
