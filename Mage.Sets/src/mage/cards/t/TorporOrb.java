package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.DontCauseTriggerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class TorporOrb extends CardImpl {

    public TorporOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Creatures entering the battlefield don't cause abilities to trigger.
        this.addAbility(new SimpleStaticAbility(new DontCauseTriggerEffect()));
    }

    private TorporOrb(final TorporOrb card) {
        super(card);
    }

    @Override
    public TorporOrb copy() {
        return new TorporOrb(this);
    }
}
