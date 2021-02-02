
package mage.cards.e;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class EladamrisVineyard extends CardImpl {

    public EladamrisVineyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");


        // At the beginning of each player's precombat main phase, add {G}{G} to that player's mana pool.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                Zone.BATTLEFIELD, new AddManaToManaPoolTargetControllerEffect(Mana.GreenMana(2), "that player's"), TargetController.ANY, false, true));
    }

    private EladamrisVineyard(final EladamrisVineyard card) {
        super(card);
    }

    @Override
    public EladamrisVineyard copy() {
        return new EladamrisVineyard(this);
    }
}
