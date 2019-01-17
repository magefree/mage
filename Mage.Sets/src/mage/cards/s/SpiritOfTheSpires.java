package mage.cards.s;

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
public final class SpiritOfTheSpires extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SpiritOfTheSpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other creatures you control with flying get +0/+1.
        Effect effect = new BoostControlledEffect(
                0, 1, Duration.WhileOnBattlefield, filter, true
        );
        effect.setText("Other creatures you control with flying get +0/+1");
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private SpiritOfTheSpires(final SpiritOfTheSpires card) {
        super(card);
    }

    @Override
    public SpiritOfTheSpires copy() {
        return new SpiritOfTheSpires(this);
    }
}
