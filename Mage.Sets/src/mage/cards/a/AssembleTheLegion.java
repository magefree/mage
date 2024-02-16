
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.permanent.token.SoldierTokenWithHaste;

/**
*
* @author LevelX2
*/
public final class AssembleTheLegion extends CardImpl {

    public AssembleTheLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{W}");

        // At the beginning of your upkeep, put a muster counter on Assemble the Legion. Then create a 1/1 red and white Soldier creature token with haste for each muster counter on Assemble the Legion.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.MUSTER.createInstance()), TargetController.YOU, false);
        ability.addEffect(new CreateTokenEffect(new SoldierTokenWithHaste(), new CountersSourceCount(CounterType.MUSTER)).concatBy("Then"));
        this.addAbility(ability);
    }

    private AssembleTheLegion(final AssembleTheLegion card) {
        super(card);
    }

    @Override
    public AssembleTheLegion copy() {
        return new AssembleTheLegion(this);
    }
}
