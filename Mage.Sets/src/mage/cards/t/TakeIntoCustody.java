package mage.cards.t;

import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TakeIntoCustody extends CardImpl {

    public TakeIntoCustody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Tap target creature. It doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("It"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TakeIntoCustody(final TakeIntoCustody card) {
        super(card);
    }

    @Override
    public TakeIntoCustody copy() {
        return new TakeIntoCustody(this);
    }
}
