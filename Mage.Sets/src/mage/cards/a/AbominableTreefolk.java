package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbominableTreefolk extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("snow permanents you control");

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public AbominableTreefolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Abominable Treefolk's power and toughness are each equal to the number of snow permanents you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(xValue, Duration.EndOfGame)
        ));

        // When Abominable Treefolk enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That creature"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private AbominableTreefolk(final AbominableTreefolk card) {
        super(card);
    }

    @Override
    public AbominableTreefolk copy() {
        return new AbominableTreefolk(this);
    }
}
