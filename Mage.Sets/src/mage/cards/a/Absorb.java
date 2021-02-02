package mage.cards.a;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Absorb extends CardImpl {

    public Absorb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}{U}");

        // Counter target spell. You gain 3 life.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Absorb(final Absorb card) {
        super(card);
    }

    @Override
    public Absorb copy() {
        return new Absorb(this);
    }
}
