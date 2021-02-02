
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AcceptableLosses extends CardImpl {

    public AcceptableLosses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // As an additional cost to cast Acceptable Losses, discard a card at random.
        this.getSpellAbility().addCost(new DiscardCardCost(true));
        // Acceptable Losses deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AcceptableLosses(final AcceptableLosses card) {
        super(card);
    }

    @Override
    public AcceptableLosses copy() {
        return new AcceptableLosses(this);
    }
}
