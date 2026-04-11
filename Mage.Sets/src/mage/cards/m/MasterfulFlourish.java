package mage.cards.m;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class MasterfulFlourish extends CardImpl {

    public MasterfulFlourish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature you control gets +1/+0 and gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
            .setText("target creature you control gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
            .setText("and gains indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private MasterfulFlourish(final MasterfulFlourish card) {
        super(card);
    }

    @Override
    public MasterfulFlourish copy() {
        return new MasterfulFlourish(this);
    }
}
