package mage.cards.i;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfernalGrasp extends CardImpl {

    public InfernalGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Destroy target creature. You lose 2 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private InfernalGrasp(final InfernalGrasp card) {
        super(card);
    }

    @Override
    public InfernalGrasp copy() {
        return new InfernalGrasp(this);
    }
}
