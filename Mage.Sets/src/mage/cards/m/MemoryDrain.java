package mage.cards.m;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class MemoryDrain extends CardImpl {

    public MemoryDrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target spell. Scry 2.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ScryEffect(2));
    }

    private MemoryDrain(final MemoryDrain card) {
        super(card);
    }

    @Override
    public MemoryDrain copy() {
        return new MemoryDrain(this);
    }
}
