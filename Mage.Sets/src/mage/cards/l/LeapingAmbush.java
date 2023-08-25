package mage.cards.l;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeapingAmbush extends CardImpl {

    public LeapingAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +1/+3 and gains reach until end of turn. Untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 3).setText("target creature gets +1/+3"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ReachAbility.getInstance()).setText("and gains reach until end of turn"));
        this.getSpellAbility().addEffect(new UntapTargetEffect("Untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LeapingAmbush(final LeapingAmbush card) {
        super(card);
    }

    @Override
    public LeapingAmbush copy() {
        return new LeapingAmbush(this);
    }
}
