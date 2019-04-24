
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author emerald000
 */
public final class AetherfluxReservoir extends CardImpl {

    public AetherfluxReservoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Whenever you cast a spell, you gain 1 life for each spell you've cast this turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(new AetherfluxReservoirDynamicValue()), false));

        // Pay 50 life: Aetherflux Reservoir deals 50 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(50), new PayLifeCost(50));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public AetherfluxReservoir(final AetherfluxReservoir card) {
        super(card);
    }

    @Override
    public AetherfluxReservoir copy() {
        return new AetherfluxReservoir(this);
    }
}

class AetherfluxReservoirDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = (CastSpellLastTurnWatcher) game.getState().getWatchers().get(CastSpellLastTurnWatcher.class.getSimpleName());
        return watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public AetherfluxReservoirDynamicValue copy() {
        return new AetherfluxReservoirDynamicValue();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "spell you've cast this turn";
    }

}