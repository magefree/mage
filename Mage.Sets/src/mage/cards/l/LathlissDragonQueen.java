package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.DragonToken2;

/**
 *
 * @author TheElk801
 */
public final class LathlissDragonQueen extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another nontoken Dragon");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Dragons");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
        filter.add(Predicates.not(new TokenPredicate()));
        filter.add(new AnotherPredicate());
        filter2.add(new SubtypePredicate(SubType.DRAGON));
    }

    public LathlissDragonQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another nontoken Dragon enters the battlefield under your control, create a 5/5 red Dragon creature token with flying.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new CreateTokenEffect(new DragonToken2()), filter
        ));
        // {1}{R}: Dragons you control get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(
                        1, 0, Duration.EndOfTurn,
                        filter2, false
                ),
                new ManaCostsImpl("{1}{R}")
        ));
    }

    public LathlissDragonQueen(final LathlissDragonQueen card) {
        super(card);
    }

    @Override
    public LathlissDragonQueen copy() {
        return new LathlissDragonQueen(this);
    }
}
