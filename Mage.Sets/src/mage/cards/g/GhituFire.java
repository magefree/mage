
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.PayMoreToCastAsThoughtItHadFlashAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 *
 */
public final class GhituFire extends CardImpl {

    public GhituFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        Effect effect = new DamageTargetEffect(ManacostVariableValue.REGULAR);
        // You may cast Ghitu Fire as though it had flash if you pay {2} more to cast it.
        Ability ability = new PayMoreToCastAsThoughtItHadFlashAbility(this, new ManaCostsImpl<>("{2}"));
        ability.addEffect(effect);
        ability.addTarget(new TargetAnyTarget());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Ghitu Fire deals X damage to any target.
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private GhituFire(final GhituFire card) {
        super(card);
    }

    @Override
    public GhituFire copy() {
        return new GhituFire(this);
    }
}
