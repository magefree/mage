package mage.cards.v;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VanishFromSight extends CardImpl {

    public VanishFromSight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Target nonland permanent's owner puts it on the top or bottom of their library. Surveil 1.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().addEffect(new SurveilEffect(1));
    }

    private VanishFromSight(final VanishFromSight card) {
        super(card);
    }

    @Override
    public VanishFromSight copy() {
        return new VanishFromSight(this);
    }
}
