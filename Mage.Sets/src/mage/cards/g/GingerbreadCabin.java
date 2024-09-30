package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.EntersBattlefieldUntappedTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.effects.common.CreateTokenEffect;
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
            = new FilterControlledPermanent(SubType.FOREST, "other Forests");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition
            = new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 3);

    public GingerbreadCabin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);

        // ({T}: Add {G}.)
        this.addAbility(new GreenManaAbility());

        // Gingerbread Cabin enters the battlefield tapped unless you control three or more other Forests.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

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
