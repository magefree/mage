package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AtraxiWarden extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }


    public AtraxiWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.EYE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Atraxi Warden enters the battlefield, exile up to one target tapped creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);

        // Suspend 5--{1}{W}
        this.addAbility(new SuspendAbility(5, new ManaCostsImpl<>("{1}{W}"), this));
    }

    private AtraxiWarden(final AtraxiWarden card) {
        super(card);
    }

    @Override
    public AtraxiWarden copy() {
        return new AtraxiWarden(this);
    }
}
