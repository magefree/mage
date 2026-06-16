package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class IronManModernMarvel extends CardImpl {


    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creatures");
    private static final FilterPermanent filter2 = new FilterArtifactCreaturePermanent("you control another artifact creature");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter2);

    public IronManModernMarvel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other artifact creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // Whenever Iron Man attacks, if you control another artifact creature, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1))
            .withInterveningIf(condition));
    }

    private IronManModernMarvel(final IronManModernMarvel card) {
        super(card);
    }

    @Override
    public IronManModernMarvel copy() {
        return new IronManModernMarvel(this);
    }
}
