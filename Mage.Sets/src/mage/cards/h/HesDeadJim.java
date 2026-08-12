package mage.cards.h;

import java.util.UUID;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class HesDeadJim extends CardImpl {

    public HesDeadJim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Destroy target creature. Surveil 1.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private HesDeadJim(final HesDeadJim card) {
        super(card);
    }

    @Override
    public HesDeadJim copy() {
        return new HesDeadJim(this);
    }
}
