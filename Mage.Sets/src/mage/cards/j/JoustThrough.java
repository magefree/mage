package mage.cards.j;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoustThrough extends CardImpl {

    public JoustThrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Joust Through deals 3 damage to target attacking or blocking creature. You gain 1 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addEffect(new GainLifeEffect(1));
    }

    private JoustThrough(final JoustThrough card) {
        super(card);
    }

    @Override
    public JoustThrough copy() {
        return new JoustThrough(this);
    }
}
