
package mage.cards.e;

import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EntrancingMelody extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with mana value X");

    public EntrancingMelody(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Gain control of target creature with converted mana cost X.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());
    }

    private EntrancingMelody(final EntrancingMelody card) {
        super(card);
    }

    @Override
    public EntrancingMelody copy() {
        return new EntrancingMelody(this);
    }
}
