package mage.cards.c;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ColossalMight extends CardImpl {

    public ColossalMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(
                4, 2, Duration.EndOfTurn
        ).setText("target creature gets +4/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public ColossalMight(final ColossalMight card) {
        super(card);
    }

    @Override
    public ColossalMight copy() {
        return new ColossalMight(this);
    }
}
