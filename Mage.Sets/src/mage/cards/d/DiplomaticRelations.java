package mage.cards.d;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiplomaticRelations extends CardImpl {

    public DiplomaticRelations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature you control gets +1/+0 and gains vigilance until end of turn. It deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0)
                .setText("target creature you control gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and gains vigilance until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("it"));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private DiplomaticRelations(final DiplomaticRelations card) {
        super(card);
    }

    @Override
    public DiplomaticRelations copy() {
        return new DiplomaticRelations(this);
    }
}
