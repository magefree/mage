
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class PeaceOfMind extends CardImpl {

    public PeaceOfMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // {W}, Discard a card: You gain 3 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(3), new ManaCostsImpl<>("{W}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private PeaceOfMind(final PeaceOfMind card) {
        super(card);
    }

    @Override
    public PeaceOfMind copy() {
        return new PeaceOfMind(this);
    }
}
