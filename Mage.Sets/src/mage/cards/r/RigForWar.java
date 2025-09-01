package mage.cards.r;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RigForWar extends CardImpl {

    public RigForWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+0 and gains first strike and reach until end of turn.
        getSpellAbility().addEffect(new BoostTargetEffect(3, 0)
                .setText("target creature gets +3/+0"));
        getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("and gains first strike"));
        getSpellAbility().addEffect(new GainAbilityTargetEffect(ReachAbility.getInstance())
                .setText("and reach until end of turn"));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RigForWar(final RigForWar card) {
        super(card);
    }

    @Override
    public RigForWar copy() {
        return new RigForWar(this);
    }
}
