package mage.cards.d;

import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyEmbrace extends CardImpl {

    public DeadlyEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Destroy target creature an opponent controls. Then draw a card for each creature that died this turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(CreaturesDiedThisTurnCount.instance)
                .setText("Then draw a card for each creature that died this turn"));
        this.getSpellAbility().addHint(CreaturesDiedThisTurnHint.instance);
    }

    private DeadlyEmbrace(final DeadlyEmbrace card) {
        super(card);
    }

    @Override
    public DeadlyEmbrace copy() {
        return new DeadlyEmbrace(this);
    }
}
