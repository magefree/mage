
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class FallOfTheTitans extends CardImpl {

    public FallOfTheTitans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{X}{R}");

        // Fall of the Titans deals X damage to each of up to two target creatures and/or players.
        this.getSpellAbility().addTarget(new TargetAnyTarget(0, 2));
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR, true, "each of up to two targets"));

        // Surge {X}{R}
        addAbility(new SurgeAbility(this, "{X}{R}"));
    }

    private FallOfTheTitans(final FallOfTheTitans card) {
        super(card);
    }

    @Override
    public FallOfTheTitans copy() {
        return new FallOfTheTitans(this);
    }
}
