package mage.cards.s;

import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SleepOfTheDead extends CardImpl {

    public SleepOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Tap target creature. It doesn't untap during its controller's next untap step.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Escape—{2}{U}, Exile three other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{2}{U}", 3));
    }

    private SleepOfTheDead(final SleepOfTheDead card) {
        super(card);
    }

    @Override
    public SleepOfTheDead copy() {
        return new SleepOfTheDead(this);
    }
}
