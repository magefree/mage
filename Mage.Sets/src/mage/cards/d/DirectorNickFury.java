package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;

/**
 *
 * @author muz
 */
public final class DirectorNickFury extends CardImpl {

    private static final FilterCard filter = new FilterCard("Hero spells");
    private static final FilterCard filter2 = new FilterCard("a Hero card");

    static {
        filter.add(SubType.HERO.getPredicate());
        filter2.add(SubType.HERO.getPredicate());
    }

    public DirectorNickFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Hero spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you attack, look at the top four cards of your library. You may reveal a Hero card from among them and put that card into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            new LookLibraryAndPickControllerEffect(4, 1, filter2, PutCards.HAND, PutCards.BOTTOM_RANDOM), 1
        ));
    }

    private DirectorNickFury(final DirectorNickFury card) {
        super(card);
    }

    @Override
    public DirectorNickFury copy() {
        return new DirectorNickFury(this);
    }
}
