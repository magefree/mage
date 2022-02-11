package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanderingMind extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public WanderingMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Wandering Mind enters the battlefield, look at the top six cards of your library. You may reveal a noncreature, nonland card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                StaticValue.get(6), false, StaticValue.get(1), filter, Zone.LIBRARY, false,
                true, false, Zone.HAND, true, false, false
        ).setBackInRandomOrder(true).setText("look at the top six cards of your library. " +
                "You may reveal a noncreature, nonland card from among them and put it into your hand. " +
                "Put the rest on the bottom of your library in a random order")));
    }

    private WanderingMind(final WanderingMind card) {
        super(card);
    }

    @Override
    public WanderingMind copy() {
        return new WanderingMind(this);
    }
}
