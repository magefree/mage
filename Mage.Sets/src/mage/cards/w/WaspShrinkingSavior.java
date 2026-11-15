package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WaspShrinkingSavior extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creature");
    private static final FilterCreaturePermanent filter2= new FilterCreaturePermanent("creature with power less than 0 on the battlefield.");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(new PowerPredicate(ComparisonType.FEWER_THAN, 0));
    }

    public WaspShrinkingSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Wasp attacks, up to one other target creature gets -3/-0 until your next turn. Then draw a card for each creature with power less than 0 on the battlefield.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(-3, 0, Duration.UntilYourNextTurn));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        ability.addEffect(
            new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter2))
                .setText("Then draw a card for each creature with power less than 0 on the battlefield")
        );
        this.addAbility(ability);
    }

    private WaspShrinkingSavior(final WaspShrinkingSavior card) {
        super(card);
    }

    @Override
    public WaspShrinkingSavior copy() {
        return new WaspShrinkingSavior(this);
    }
}
