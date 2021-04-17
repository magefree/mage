package mage.cards.g;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldUntappedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GingerbreadCabin extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.FOREST);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 3);

    public GingerbreadCabin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);

        // ({T}: Add {G}.)
        this.addAbility(new GreenManaAbility());

        // Gingerbread Cabin enters the battlefield tapped unless you control three or more other Forests.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new TapSourceEffect(), condition),
                "tapped unless you control three or more other Forests"
        ));

        // When Gingerbread Cabin enters the battlefield untapped, create a Food token.
        this.addAbility(new EntersBattlefieldUntappedTriggeredAbility(new CreateTokenEffect(new FoodToken()), false));
    }

    private GingerbreadCabin(final GingerbreadCabin card) {
        super(card);
    }

    @Override
    public GingerbreadCabin copy() {
        return new GingerbreadCabin(this);
    }
}
