
package mage.cards.w;

import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class WordOfBinding extends CardImpl {

    public WordOfBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Tap X target creatures.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap X target creatures"));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WordOfBinding(final WordOfBinding card) {
        super(card);
    }

    @Override
    public WordOfBinding copy() {
        return new WordOfBinding(this);
    }
}
