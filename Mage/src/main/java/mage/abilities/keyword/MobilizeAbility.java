package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.RedWarriorToken;
import mage.util.CardUtil;

/**
 * @author balazskristof
 */
public class MobilizeAbility extends AttacksTriggeredAbility {

    public MobilizeAbility(int count) {
        this(StaticValue.get(count));
    }

    public MobilizeAbility(DynamicValue count) {
        super(new MobilizeEffect(count), false, makeText(count));
    }

    protected MobilizeAbility(final MobilizeAbility ability) {
        super(ability);
    }

    @Override
    public MobilizeAbility copy() {
        return new MobilizeAbility(this);
    }

    private static String makeText(DynamicValue amount) {
        String message;
        String numToText;
        boolean plural;
        if (amount instanceof StaticValue) {
            int count = ((StaticValue) amount).getValue();
            message = "" + count;
            numToText = CardUtil.numberToText(count, "a");
            plural = count > 1;
        } else {
            message = "X, where X is " + amount.getMessage() + '.';
            numToText = "X";
            plural = true;
        }
        return "Mobilize " + message + " <i>(Whenever this creature attacks, create " +
                numToText + " tapped and attacking 1/1 red Warrior creature token" + (plural ? "s" : "") +
                ". Sacrifice " + (plural ? "them" : "it") + " at the beginning of the next end step.)</i>";
    }
}

class MobilizeEffect extends OneShotEffect {

    private final DynamicValue count;

    MobilizeEffect(DynamicValue count) {
        super(Outcome.Benefit);
        this.count = count;
    }

    private MobilizeEffect(final MobilizeEffect effect) {
        super(effect);
        this.count = effect.count;
    }

    @Override
    public MobilizeEffect copy() {
        return new MobilizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new RedWarriorToken(), this.count, true, true);
        effect.apply(game, source);
        effect.removeTokensCreatedAt(game, source, false, PhaseStep.END_TURN, TargetController.ANY);
        return true;
    }
}
