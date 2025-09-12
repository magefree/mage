package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.FishNoAbilityToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Knightfisher extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.BIRD, "another nontoken Bird you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public Knightfisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another nontoken Bird you control enters, create a 1/1 blue Fish creature token.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new CreateTokenEffect(new FishNoAbilityToken()), filter));
    }

    private Knightfisher(final Knightfisher card) {
        super(card);
    }

    @Override
    public Knightfisher copy() {
        return new Knightfisher(this);
    }
}
