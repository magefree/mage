
package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author North
 */
public final class BurningOil extends CardImpl {

    public BurningOil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Burning Oil deals 3 damage to target attacking or blocking creature.
        this.getSpellAbility().addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        // Flashback {3}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{3}{W}")));
    }

    private BurningOil(final BurningOil card) {
        super(card);
    }

    @Override
    public BurningOil copy() {
        return new BurningOil(this);
    }
}
