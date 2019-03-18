
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.CardsInAnyLibraryCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ShelldockIsle extends CardImpl {

    public ShelldockIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Hideaway
        this.addAbility(new HideawayAbility());
        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());
        // {U}, {tap}: You may play the exiled card without paying its mana cost if a library has twenty or fewer cards in it.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new HideawayPlayEffect(), new ManaCostsImpl("{U}"), new CardsInAnyLibraryCondition(ComparisonType.FEWER_THAN, 21));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public ShelldockIsle(final ShelldockIsle card) {
        super(card);
    }

    @Override
    public ShelldockIsle copy() {
        return new ShelldockIsle(this);
    }
}
