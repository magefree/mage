package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.CatToken3;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArahboTheFirstFang extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.CAT, "Cats");
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.CAT, "nontoken Cat you control");

    static {
        filter2.add(TokenPredicate.FALSE);
    }

    public ArahboTheFirstFang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Cats you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever Arahbo or another nontoken Cat you control enters, create a 1/1 white Cat creature token.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new CatToken3()), filter2, false, false
        ));
    }

    private ArahboTheFirstFang(final ArahboTheFirstFang card) {
        super(card);
    }

    @Override
    public ArahboTheFirstFang copy() {
        return new ArahboTheFirstFang(this);
    }
}
