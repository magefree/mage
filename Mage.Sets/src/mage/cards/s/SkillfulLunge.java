package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class SkillfulLunge extends CardImpl {

    public SkillfulLunge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Target creature gets +2/+0 and gains first strike until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains first strike until end of turn"));
    }

    private SkillfulLunge(final SkillfulLunge card) {
        super(card);
    }

    @Override
    public SkillfulLunge copy() {
        return new SkillfulLunge(this);
    }
}
