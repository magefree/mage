package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class DeadlyVisit extends CardImpl {

    public DeadlyVisit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Surveil 2.
        this.getSpellAbility().addEffect(new SurveilEffect(2).concatBy("<br>"));
    }

    private DeadlyVisit(final DeadlyVisit card) {
        super(card);
    }

    @Override
    public DeadlyVisit copy() {
        return new DeadlyVisit(this);
    }
}
