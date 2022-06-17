
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SinisterConcoction extends CardImpl {

    public SinisterConcoction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");

        // {B}, Pay 1 life, Put the top card of your library into your graveyard, Discard a card, Sacrifice Sinister Concoction: Destroy target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new PayLifeCost(1));
        ability.addCost(new MillCardsCost());
        ability.addCost(new DiscardCardCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SinisterConcoction(final SinisterConcoction card) {
        super(card);
    }

    @Override
    public SinisterConcoction copy() {
        return new SinisterConcoction(this);
    }
}
