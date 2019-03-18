
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class HowltoothHollow extends CardImpl {

    public HowltoothHollow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Hideaway
        this.addAbility(new HideawayAbility());
        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
        
        // {B}, {tap}: You may play the exiled card without paying its mana cost if each player has no cards in hand.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, 
                new HideawayPlayEffect(), 
                new ManaCostsImpl("{B}"), 
                new CardsInHandCondition(ComparisonType.EQUAL_TO, 0, null, TargetController.ANY));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);        
    }

    public HowltoothHollow(final HowltoothHollow card) {
        super(card);
    }

    @Override
    public HowltoothHollow copy() {
        return new HowltoothHollow(this);
    }
}
