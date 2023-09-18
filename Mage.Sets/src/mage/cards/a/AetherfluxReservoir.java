package mage.cards.a;

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
import mage.game.Game;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class AetherfluxReservoir extends CardImpl {

    public AetherfluxReservoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Whenever you cast a spell, you gain 1 life for each spell you've cast this turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(
                AetherfluxReservoirDynamicValue.instance, "you gain 1 life for each spell you've cast this turn"
        ), false));

        // Pay 50 life: Aetherflux Reservoir deals 50 damage to any target.
        Ability abilityPayLife = new SimpleActivatedAbility(new DamageTargetEffect(50), new PayLifeCost(50));
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

enum AetherfluxReservoirDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getState()
                .getWatcher(CastSpellLastTurnWatcher.class)
                .getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public AetherfluxReservoirDynamicValue copy() {
        return this;
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