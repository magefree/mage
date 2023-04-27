
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LoneFox
 */
public final class Mortiphobia extends CardImpl {

    public Mortiphobia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");

        // {1}{B}, Discard a card: Exile target card from a graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
        // {1}{B}, Sacrifice Mortiphobia: Exile target card from a graveyard.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private Mortiphobia(final Mortiphobia card) {
        super(card);
    }

    @Override
    public Mortiphobia copy() {
        return new Mortiphobia(this);
    }
}
