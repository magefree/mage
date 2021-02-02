
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author emerald000
 */
public final class IllusionsOfGrandeur extends CardImpl {

    public IllusionsOfGrandeur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");


        // Cumulative upkeep {2}
        this.addAbility(new CumulativeUpkeepAbility(new GenericManaCost(2)));
        
        // When Illusions of Grandeur enters the battlefield, you gain 20 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(20)));
        
        // When Illusions of Grandeur leaves the battlefield, you lose 20 life.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(20), false));
    }

    private IllusionsOfGrandeur(final IllusionsOfGrandeur card) {
        super(card);
    }

    @Override
    public IllusionsOfGrandeur copy() {
        return new IllusionsOfGrandeur(this);
    }
}
