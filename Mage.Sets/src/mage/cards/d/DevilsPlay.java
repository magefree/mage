
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class DevilsPlay extends CardImpl {

    public DevilsPlay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");


        // Devil's Play deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Flashback {X}{R}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{X}{R}{R}{R}")));
    }

    private DevilsPlay(final DevilsPlay card) {
        super(card);
    }

    @Override
    public DevilsPlay copy() {
        return new DevilsPlay(this);
    }
}
