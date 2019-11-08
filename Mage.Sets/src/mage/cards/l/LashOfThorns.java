package mage.cards.l;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LashOfThorns extends CardImpl {

    public LashOfThorns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +2/+1 and gains deathtouch until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 1, Duration.EndOfTurn
        ).setText("target creature gets +2/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains deathtouch until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LashOfThorns(final LashOfThorns card) {
        super(card);
    }

    @Override
    public LashOfThorns copy() {
        return new LashOfThorns(this);
    }
}
