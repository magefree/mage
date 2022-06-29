
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DiscardCostCardManaValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox

 */
public final class Pyromancy extends CardImpl {

    public Pyromancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}");

        // {3}, Discard a card at random: Pyromancy deals damage to any target equal to the converted mana cost of the discarded card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(DiscardCostCardManaValue.instance), new ManaCostsImpl<>("{3}"));
        ability.addTarget(new TargetAnyTarget());
        ability.addCost(new DiscardCardCost(true));
        this.addAbility(ability);
    }

    private Pyromancy(final Pyromancy card) {
        super(card);
    }

    @Override
    public Pyromancy copy() {
        return new Pyromancy(this);
    }
}
