package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author ilyagart
 */
public final class UreniOfTheUnwritten extends CardImpl {

    static final FilterCreatureCard filter = new FilterCreatureCard("Dragon creature card");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public UreniOfTheUnwritten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Ureni enters or attacks, look at the top eight cards of your library. You may put a Dragon creature card from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        Effect effect = new LookLibraryAndPickControllerEffect(8, 1, filter, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM);
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(effect, false));
    }

    private UreniOfTheUnwritten(final UreniOfTheUnwritten card) {
        super(card);
    }

    @Override
    public UreniOfTheUnwritten copy() {
        return new UreniOfTheUnwritten(this);
    }
}