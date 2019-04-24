package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;
import mage.abilities.effects.Effect;

/**
 * @author TheElk801
 */
public final class WindstormDrake extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WindstormDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other creatures you control with flying get +1/+0.
        Effect effect = new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter, true
        );
        effect.setText("Other creatures you control with flying get +1/+0");
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private WindstormDrake(final WindstormDrake card) {
        super(card);
    }

    @Override
    public WindstormDrake copy() {
        return new WindstormDrake(this);
    }
}
