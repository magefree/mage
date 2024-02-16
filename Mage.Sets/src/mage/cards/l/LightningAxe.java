
package mage.cards.l;

import java.util.UUID;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LightningAxe extends CardImpl {

    public LightningAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // As an additional cost to cast Lightning Axe, discard a card or pay {5}.
        this.getSpellAbility().addCost(new OrCost("discard a card or pay {5}", new DiscardCardCost(), new GenericManaCost(5)));
        // Lightning Axe deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LightningAxe(final LightningAxe card) {
        super(card);
    }

    @Override
    public LightningAxe copy() {
        return new LightningAxe(this);
    }
}
