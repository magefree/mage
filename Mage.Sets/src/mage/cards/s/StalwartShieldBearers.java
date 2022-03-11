package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author North
 */
public final class StalwartShieldBearers extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control with defender");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public StalwartShieldBearers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                0, 2, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private StalwartShieldBearers(final StalwartShieldBearers card) {
        super(card);
    }

    @Override
    public StalwartShieldBearers copy() {
        return new StalwartShieldBearers(this);
    }
}
