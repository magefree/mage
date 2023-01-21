package mage.cards.t;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class TyvarsStand extends CardImpl {

    public TyvarsStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}");

        // Target creature you control gets +X/+X and gains hexproof and indestructible until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                ManacostVariableValue.REGULAR, ManacostVariableValue.REGULAR, Duration.EndOfTurn
        ));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("and gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private TyvarsStand(final TyvarsStand card) {
        super(card);
    }

    @Override
    public TyvarsStand copy() {
        return new TyvarsStand(this);
    }
}
