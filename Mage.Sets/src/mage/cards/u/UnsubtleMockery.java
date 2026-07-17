package mage.cards.u;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnsubtleMockery extends CardImpl {

    public UnsubtleMockery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Unsubtle Mockery deals 4 damage to target creature. Surveil 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private UnsubtleMockery(final UnsubtleMockery card) {
        super(card);
    }

    @Override
    public UnsubtleMockery copy() {
        return new UnsubtleMockery(this);
    }
}
