package mage.cards.u;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class UnchartedVoyage extends CardImpl {

    public UnchartedVoyage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Target creature's owner puts it on their choice of the top or bottom of their library.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Surveil 1.
        this.getSpellAbility().addEffect(new SurveilEffect(1).concatBy("<br>"));
    }

    private UnchartedVoyage(final UnchartedVoyage card) {
        super(card);
    }

    @Override
    public UnchartedVoyage copy() {
        return new UnchartedVoyage(this);
    }
}
