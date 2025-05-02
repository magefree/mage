
package mage.cards.g;

import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Gridlock extends CardImpl {

    public Gridlock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Tap X target nonland permanents.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap X target nonland permanents"));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private Gridlock(final Gridlock card) {
        super(card);
    }

    @Override
    public Gridlock copy() {
        return new Gridlock(this);
    }
}
