package mage.cards.j;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JayasGreeting extends CardImpl {

    public JayasGreeting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Jaya's Greeting deals 3 damage to target creature. Scry 1.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private JayasGreeting(final JayasGreeting card) {
        super(card);
    }

    @Override
    public JayasGreeting copy() {
        return new JayasGreeting(this);
    }
}
