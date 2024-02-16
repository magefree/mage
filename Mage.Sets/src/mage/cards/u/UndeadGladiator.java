
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class UndeadGladiator extends CardImpl {

    public UndeadGladiator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {1}{B}, Discard a card: Return Undead Gladiator from your graveyard to your hand. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.GRAVEYARD, 
                new ReturnSourceFromGraveyardToHandEffect(), 
                new ManaCostsImpl<>("{1}{B}"), 
                new IsStepCondition(PhaseStep.UPKEEP), null);
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
        
        // Cycling {1}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private UndeadGladiator(final UndeadGladiator card) {
        super(card);
    }

    @Override
    public UndeadGladiator copy() {
        return new UndeadGladiator(this);
    }
}
