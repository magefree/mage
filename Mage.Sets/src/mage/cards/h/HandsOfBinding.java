package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import static mage.filter.StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE;

/**
 *
 * @author Plopman
 */
public final class HandsOfBinding extends CardImpl {

    public HandsOfBinding (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        //Tap target creature an opponent controls. That creature doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("that creature"));
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_OPPONENTS_PERMANENT_CREATURE));
        //Cipher 
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private HandsOfBinding(final HandsOfBinding card) {
        super(card);
    }

    @Override
    public HandsOfBinding copy() {
        return new HandsOfBinding(this);
    }
}
