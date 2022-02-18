package mage.cards.k;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class KindledFury extends CardImpl {

    public KindledFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 0, Duration.EndOfTurn
        ).setText("target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private KindledFury(final KindledFury card) {
        super(card);
    }

    @Override
    public KindledFury copy() {
        return new KindledFury(this);
    }
}
