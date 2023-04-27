package mage.cards.u;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class UnleashShell extends CardImpl {

    public UnleashShell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Unleash Shell deals 5 damage to target creature or planeswalker and 2 damage to that permanent's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new DamageTargetControllerEffect(2).setText("and 2 damage to that permanent's controller"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private UnleashShell(final UnleashShell card) {
        super(card);
    }

    @Override
    public UnleashShell copy() {
        return new UnleashShell(this);
    }
}
