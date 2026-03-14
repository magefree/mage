package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

import java.util.UUID;

/**
 *
 * @author MTGfan
 */
public final class Simulacrum extends CardImpl {

    public Simulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");


        // You gain life equal to the damage dealt to you this turn. Simulacrum deals damage to target creature you control equal to the damage dealt to you this turn.
        this.getSpellAbility().addEffect(new GainLifeEffect(SimulacrumAmount.instance, "You gain life equal to the damage dealt to you this turn."));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new DamageTargetEffect(SimulacrumAmount.instance)
                .setText("{this} deals damage to target creature you control equal to the damage dealt to you this turn"));
        this.getSpellAbility().addWatcher(new AmountOfDamageAPlayerReceivedThisTurnWatcher());
    }

    private Simulacrum(final Simulacrum card) {
        super(card);
    }

    @Override
    public Simulacrum copy() {
        return new Simulacrum(this);
    }
}

enum SimulacrumAmount implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        AmountOfDamageAPlayerReceivedThisTurnWatcher watcher = game.getState().getWatcher(AmountOfDamageAPlayerReceivedThisTurnWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfDamageReceivedThisTurn(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public SimulacrumAmount copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
