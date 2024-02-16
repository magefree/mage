package mage.cards.m;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MomentOfDefiance extends CardImpl {

    public MomentOfDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target creature gets +2/+1 and gains lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 1)
                .setText("target creature gets +2/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
                .setText("and gains lifelink until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private MomentOfDefiance(final MomentOfDefiance card) {
        super(card);
    }

    @Override
    public MomentOfDefiance copy() {
        return new MomentOfDefiance(this);
    }
}
