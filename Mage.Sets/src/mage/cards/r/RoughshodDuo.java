package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoughshodDuo extends CardImpl {

    public RoughshodDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.RACCOON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you expend 4, target creature you control gets +1/+1 and gains trample until end of turn.
        Ability ability = new ExpendTriggeredAbility(
                new BoostTargetEffect(1, 1)
                        .setText("target creature you control gets +1/+1"),
                ExpendTriggeredAbility.Expend.FOUR
        );
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
                .setText("and gains trample until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private RoughshodDuo(final RoughshodDuo card) {
        super(card);
    }

    @Override
    public RoughshodDuo copy() {
        return new RoughshodDuo(this);
    }
}
