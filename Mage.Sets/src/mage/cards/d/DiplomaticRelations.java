package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 * @author vcommero
 */
public final class DiplomaticRelations extends CardImpl {

    public DiplomaticRelations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature gets +1/+0 and gains vigilance until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
            1, 0, Duration.EndOfTurn
        ).setText("Target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
            VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains vigilance until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // It deals damage equal to its power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("It"));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent()); // second target for effect
    }

    private DiplomaticRelations(final DiplomaticRelations card) {
        super(card);
    }

    @Override
    public DiplomaticRelations copy() {
        return new DiplomaticRelations(this);
    }
}
