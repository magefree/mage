
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GripOfTheRoil extends CardImpl {

    public GripOfTheRoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Tap target creature. It doesn't untap during its controller's next untap step.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

        // Surge {1}{U}
        addAbility(new SurgeAbility(this, "{1}{U}"));
    }

    private GripOfTheRoil(final GripOfTheRoil card) {
        super(card);
    }

    @Override
    public GripOfTheRoil copy() {
        return new GripOfTheRoil(this);
    }
}
