package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetPerpetuallyEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BoostSourcePerpetuallyEffect extends OneShotEffect {

    private final DynamicValue power;
    private final DynamicValue toughness;

    public BoostSourcePerpetuallyEffect(int power, int toughness) {
        this(StaticValue.get(power), StaticValue.get(toughness));
    }

    public BoostSourcePerpetuallyEffect(DynamicValue power, DynamicValue toughness) {
        super(Outcome.BoostCreature);
        NumberFormat plusMinusNF = new DecimalFormat("+#;-#");
        int addPower = ((StaticValue) power).getValue();
        int addToughness = ((StaticValue) toughness).getValue();
        staticText = "{this} perpetually gets " + plusMinusNF.format(addPower) + "/" + plusMinusNF.format(addToughness);
        this.power = power;
        this.toughness = toughness;
    }

    protected BoostSourcePerpetuallyEffect(final BoostSourcePerpetuallyEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public BoostSourcePerpetuallyEffect copy() {
        return new BoostSourcePerpetuallyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(source.getSourceId());
            if (!controller.getHand().isEmpty()) {
                game.addEffect(new BoostTargetPerpetuallyEffect(power, toughness)
                        .setTargetPointer(new FixedTarget(card, game)), source);

            }
            return true;
        }
        return false;
    }
}
