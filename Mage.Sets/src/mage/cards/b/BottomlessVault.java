
package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author anonymous
 */
public final class BottomlessVault extends CardImpl {

    public BottomlessVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Bottomless Vault enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // You may choose not to untap Bottomless Vault during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());
        // At the beginning of your upkeep, if Bottomless Vault is tapped, put a storage counter on it.
        OneShotEffect addStorageCounter = new AddCountersSourceEffect(CounterType.STORAGE.createInstance());
        Effect effect = new ConditionalOneShotEffect(addStorageCounter, SourceTappedCondition.TAPPED, "if {this} is tapped, put a storage counter on it");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false));
        // {tap}, Remove any number of storage counters from Bottomless Vault: Add {B} for each storage counter removed this way.
        Ability ability = new DynamicManaAbility(
                Mana.BlackMana(1),
                RemovedCountersForCostValue.instance,
                new TapSourceCost(),
                "Add {B} for each storage counter removed this way",
                true, new CountersSourceCount(CounterType.STORAGE));
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.STORAGE.createInstance(),
                "Remove any number of storage counters from {this}"));
        this.addAbility(ability);
    }

    private BottomlessVault(final BottomlessVault card) {
        super(card);
    }

    @Override
    public BottomlessVault copy() {
        return new BottomlessVault(this);
    }
}
