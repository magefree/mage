

package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AwakeningZone extends CardImpl {

    public AwakeningZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // At the beginning of your upkeep, you may create a 0/1 colorless Eldrazi Spawn creature token. It has “Sacrifice this creature: Add {C}.”
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(
                new EldraziSpawnToken()).withTextOptions(true), TargetController.YOU, true));
    }

    private AwakeningZone(final AwakeningZone card) {
        super(card);
    }

    @Override
    public AwakeningZone copy() {
        return new AwakeningZone(this);
    }

}
