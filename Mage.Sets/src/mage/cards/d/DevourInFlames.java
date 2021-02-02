
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class DevourInFlames extends CardImpl {

    public DevourInFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // As an additional cost to cast Devour in Flames, return a land you control to its owner's hand.
        this.getSpellAbility().addCost(new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(new FilterControlledLandPermanent("land"))));

        // Devour in Flames deals 5 damage to target creature or planeswalker.
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
    }

    private DevourInFlames(final DevourInFlames card) {
        super(card);
    }

    @Override
    public DevourInFlames copy() {
        return new DevourInFlames(this);
    }
}
