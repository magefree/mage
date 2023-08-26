package mage.cards.k;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KindledHeroism extends CardImpl {

    public KindledHeroism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +1/+0 and gains first strike until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 0, Duration.EndOfTurn
        ).setText("Target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private KindledHeroism(final KindledHeroism card) {
        super(card);
    }

    @Override
    public KindledHeroism copy() {
        return new KindledHeroism(this);
    }
}
