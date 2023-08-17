package mage.abilities.effects.common;


import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @Author Susucr
 * <p>
 * Have the Controller of target permanent (or LKI controller) create Tokens.
 */
public class CreateTokenControllerTargetPermanentEffect extends OneShotEffect {
    private final Token token;
    private final DynamicValue amount;
    private final boolean tapped;
    private final boolean attacking;

    public CreateTokenControllerTargetPermanentEffect(Token token) {
        this(token, StaticValue.get(1));
    }

    public CreateTokenControllerTargetPermanentEffect(Token token, int amount) {
        this(token, StaticValue.get(amount));
    }

    public CreateTokenControllerTargetPermanentEffect(Token token, DynamicValue amount) {
        this(token, amount, false, false);
    }

    public CreateTokenControllerTargetPermanentEffect(Token token, int amount, boolean tapped) {
        this(token, amount, tapped, false);
    }

    public CreateTokenControllerTargetPermanentEffect(Token token, int amount, boolean tapped, boolean attacking) {
        this(token, StaticValue.get(amount), tapped, attacking);
    }

    public CreateTokenControllerTargetPermanentEffect(Token token, DynamicValue amount, boolean tapped, boolean attacking) {
        super(Outcome.Neutral);
        this.token = token;
        this.amount = amount.copy();
        this.tapped = tapped;
        this.attacking = attacking;
        this.staticText = setText();
    }

    protected CreateTokenControllerTargetPermanentEffect(final CreateTokenControllerTargetPermanentEffect effect) {
        super(effect);
        this.token = effect.token.copy();
        this.amount = effect.amount.copy();
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
    }

    @Override
    public CreateTokenControllerTargetPermanentEffect copy() {
        return new CreateTokenControllerTargetPermanentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature != null) {
            Player controllerOfTarget = game.getPlayer(creature.getControllerId());
            if (controllerOfTarget != null) {
                int value = amount.calculate(game, source, this);
                return token.putOntoBattlefield(value, game, source, controllerOfTarget.getId(), tapped, attacking);
            }
        }
        return false;
    }

    private String setText() {
        String text = "Its controller creates ";

        if (token.getDescription().contains(", a legendary")) {
            text += token.getDescription();
            return text;
        }

        if (amount.toString().equals("1")) {
            if (tapped && !attacking) {
                text += "a tapped " + token.getDescription();
            } else {
                text += CardUtil.addArticle(token.getDescription());
            }
        } else {
            text += CardUtil.numberToText(amount.toString()) + " ";
            if (tapped && !attacking) {
                text += "tapped ";
            }
            text += token.getDescription().replace("token. It has", "tokens. They have");
            if (token.getDescription().endsWith("token")) {
                text += "s";
            }
            text.replace("token ", "tokens ");
        }

        if (attacking) {
            if (amount.toString().equals("1")) {
                text += " that's";
            } else {
                text += " that are";
            }
            if (tapped) {
                text += " tapped and";
            }
            text += " attacking";
        }

        String message = amount.getMessage();
        if (!message.isEmpty()) {
            if (amount.toString().equals("X")) {
                text += ", where X is ";
            } else {
                text += " for each ";
            }
        }
        text += message;

        return text;
    }
}
