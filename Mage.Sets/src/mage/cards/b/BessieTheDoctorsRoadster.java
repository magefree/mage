package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BessieTheDoctorsRoadster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public BessieTheDoctorsRoadster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Bessie attacks, another target legendary creature can't be blocked this turn.
        Ability ability = new AttacksTriggeredAbility(new CantBeBlockedTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private BessieTheDoctorsRoadster(final BessieTheDoctorsRoadster card) {
        super(card);
    }

    @Override
    public BessieTheDoctorsRoadster copy() {
        return new BessieTheDoctorsRoadster(this);
    }
}
