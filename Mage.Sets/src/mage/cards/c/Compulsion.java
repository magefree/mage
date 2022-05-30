
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Jgod
 */
public final class Compulsion extends CardImpl {

    public Compulsion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // {1}{U}, Discard a card: Draw a card.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{U}"));
        ability1.addCost(new DiscardTargetCost(new TargetCardInHand(1, new FilterCard("a card"))));
        this.addAbility(ability1);
        
        // {1}{U}, Sacrifice Compulsion: Draw a card.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{U}"));
        ability2.addCost(new SacrificeSourceCost());
        this.addAbility(ability2);
    }

    private Compulsion(final Compulsion card) {
        super(card);
    }

    @Override
    public Compulsion copy() {
        return new Compulsion(this);
    }
}
