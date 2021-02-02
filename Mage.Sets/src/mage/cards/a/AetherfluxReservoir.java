
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
import mage.abilities.hint.ValueHint;
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
        Ability abilityGainLife = new SpellCastControllerTriggeredAbility(new GainLifeEffect(new AetherfluxReservoirDynamicValue()), false);
        abilityGainLife.addHint(new ValueHint("You've cast spells this turn", new AetherfluxReservoirDynamicValue()));
        this.addAbility(abilityGainLife);

        // Pay 50 life: Aetherflux Reservoir deals 50 damage to any target.
        Ability abilityPayLife = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(50), new PayLifeCost(50));
        abilityPayLife.addTarget(new TargetAnyTarget());
        this.addAbility(abilityPayLife);
    }

    private AetherfluxReservoir(final AetherfluxReservoir card) {
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
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if(watcher != null) {
            return watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
        }
        return 0;
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