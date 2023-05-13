package mage.cards.j;

import java.util.UUID;

import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTarget;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public final class JayasImmolatingInferno extends CardImpl {

    public JayasImmolatingInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility());

        // Jaya's Immolating Inferno deals X damage to each of up to three targets.
        Effect effect = new DamageTargetEffect(ManacostVariableValue.REGULAR);
        effect.setText("{this} deals X damage to each of up to three targets");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget(1, 3));
    }

    private JayasImmolatingInferno(final JayasImmolatingInferno card) {
        super(card);
    }

    @Override
    public JayasImmolatingInferno copy() {
        return new JayasImmolatingInferno(this);
    }
}
