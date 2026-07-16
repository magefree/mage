package mage.cards.b;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class BionicBlow extends CardImpl {

    public BionicBlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Target creature you control gets +X/+0 until end of turn. Then it deals damage equal to its power to up to one other target creature.
        this.getSpellAbility().addEffect(new BoostTargetEffect(GetXValue.instance, StaticValue.get(0))
            .setText("target creature you control gets +X/+0 until end of turn"));
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("Then it"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(
            0, 1, StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2
        ).setTargetTag(2));
    }

    private BionicBlow(final BionicBlow card) {
        super(card);
    }

    @Override
    public BionicBlow copy() {
        return new BionicBlow(this);
    }
}
