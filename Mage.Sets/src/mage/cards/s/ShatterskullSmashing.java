package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetAmount;
import mage.target.common.TargetCreatureOrPlaneswalkerAmount;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShatterskullSmashing extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.instance, 2);

    public ShatterskullSmashing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Shatterskull Smashing deals X damage divided as you choose among up to two target creatures and/or planeswalkers. If X is 6 or more, Shatterskull Smashing deals twice X damage divided as you choose among them instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageMultiEffect(xValue), new DamageMultiEffect(ManacostVariableValue.instance),
                ShatterskullSmashingCondition.instance, "{this} deals X damage divided as you choose " +
                "among up to two target creatures and/or planeswalkers. If X is 6 or more, " +
                "{this} deals twice X damage divided as you choose among them instead."
        ));
        this.getSpellAbility().setTargetAdjuster(ShatterskullSmashingAdjuster.instance);
    }

    private ShatterskullSmashing(final ShatterskullSmashing card) {
        super(card);
    }

    @Override
    public ShatterskullSmashing copy() {
        return new ShatterskullSmashing(this);
    }
}

enum ShatterskullSmashingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source.getManaCostsToPay().getX() >= 6;
    }
}

enum ShatterskullSmashingAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        TargetAmount target;
        if (ability.getManaCostsToPay().getX() >= 6) {
            target = new TargetCreatureOrPlaneswalkerAmount(2 * ability.getManaCostsToPay().getX());
        } else {
            target = new TargetCreatureOrPlaneswalkerAmount(ability.getManaCostsToPay().getX());
        }
        target.setMinNumberOfTargets(0);
        target.setMaxNumberOfTargets(2);
        ability.addTarget(target);
    }
}
