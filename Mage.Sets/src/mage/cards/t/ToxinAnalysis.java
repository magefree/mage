package mage.cards.t;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToxinAnalysis extends CardImpl {

    public ToxinAnalysis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gains deathtouch and lifelink until end of turn. Investigate.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DeathtouchAbility.getInstance())
                .setText("target creature gains deathtouch"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("and lifelink until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new InvestigateEffect());
    }

    private ToxinAnalysis(final ToxinAnalysis card) {
        super(card);
    }

    @Override
    public ToxinAnalysis copy() {
        return new ToxinAnalysis(this);
    }
}
