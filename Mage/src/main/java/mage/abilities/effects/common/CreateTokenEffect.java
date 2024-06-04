package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CreateTokenEffect extends OneShotEffect {

    private final Token token;
    private final DynamicValue amount;
    private final boolean tapped;
    private final boolean attacking;
    private String additionalRules;
    private boolean oldPhrasing = false; // true for "token. It has " instead of "token with "
    private List<UUID> lastAddedTokenIds = new ArrayList<>();
    private CounterType counterType;
    private DynamicValue numberOfCounters;

    public CreateTokenEffect(Token token) {
        this(token, StaticValue.get(1));
    }

    public CreateTokenEffect(Token token, int amount) {
        this(token, StaticValue.get(amount));
    }

    public CreateTokenEffect(Token token, DynamicValue amount) {
        this(token, amount, false, false);
    }

    public CreateTokenEffect(Token token, int amount, boolean tapped) {
        this(token, amount, tapped, false);
    }

    public CreateTokenEffect(Token token, int amount, boolean tapped, boolean attacking) {
        this(token, StaticValue.get(amount), tapped, attacking);
    }

    public CreateTokenEffect(Token token, DynamicValue amount, boolean tapped, boolean attacking) {
        super(Outcome.PutCreatureInPlay);
        this.token = token;
        this.amount = amount.copy();
        this.tapped = tapped;
        this.attacking = attacking;
        setText();
    }

    protected CreateTokenEffect(final CreateTokenEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.token = effect.token.copy();
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
        this.lastAddedTokenIds.addAll(effect.lastAddedTokenIds);
        this.counterType = effect.counterType;
        this.numberOfCounters = effect.numberOfCounters;
        this.additionalRules = effect.additionalRules;
        this.oldPhrasing = effect.oldPhrasing;
    }

    public CreateTokenEffect entersWithCounters(CounterType counterType, DynamicValue numberOfCounters) {
        this.counterType = counterType;
        this.numberOfCounters = numberOfCounters;
        return this;
    }

    @Override
    public CreateTokenEffect copy() {
        return new CreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = amount.calculate(game, source, this);
        token.putOntoBattlefield(value, game, source, source.getControllerId(), tapped, attacking);
        this.lastAddedTokenIds = token.getLastAddedTokenIds();
        // TODO: Workaround to add counters to all created tokens, necessary for correct interactions with cards like Chatterfang, Squirrel General and Ochre Jelly / Printlifter Ooze. See #10786
        if (counterType != null) {
            for (UUID tokenId : lastAddedTokenIds) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    tokenPermanent.addCounters(counterType.createInstance(numberOfCounters.calculate(game, source, this)), source.getControllerId(), source, game);
                }
            }
        }

        return true;
    }

    public List<UUID> getLastAddedTokenIds() {
        return lastAddedTokenIds;
    }

    public void exileTokensCreatedAtNextEndStep(Game game, Ability source) {
        for (UUID tokenId : this.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                ExileTargetEffect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
                exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
            }
        }
    }

    public void exileTokensCreatedAtEndOfCombat(Game game, Ability source) {
        for (UUID tokenId : this.getLastAddedTokenIds()) {
            Permanent tokenPermanent = game.getPermanent(tokenId);
            if (tokenPermanent != null) {
                ExileTargetEffect exileEffect = new ExileTargetEffect(null, "", Zone.BATTLEFIELD);
                exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), source);
            }
        }
    }

    /**
     * For adding reminder text to the effect
     */
    public CreateTokenEffect withAdditionalRules(String additionalRules) {
        this.additionalRules = additionalRules;
        setText();
        return this;
    }

    /**
     * For older cards that use the phrasing "token. It has " rather than "token with ".
     */
    public CreateTokenEffect withTextOptions(boolean oldPhrasing) {
        this.oldPhrasing = oldPhrasing;
        setText();
        return this;
    }

    private void setText() {
        String tokenDescription = token.getDescription();
        boolean singular = amount.toString().equals("1");
        if (tokenDescription.contains(", a legendary")) {
            staticText = "create " + tokenDescription;
            return;
        }
        if (oldPhrasing) {
            tokenDescription = tokenDescription.replace("token with \"",
                    singular ? "token. It has \"" : "tokens. They have \"");
        }
        StringBuilder sb = new StringBuilder("create ");
        if (singular) {
            if (tapped && !attacking) {
                sb.append("a tapped ");
                sb.append(tokenDescription);
            } else {
                sb.append(CardUtil.addArticle(tokenDescription));
            }
        } else {
            sb.append(CardUtil.numberToText(amount.toString())).append(' ');
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append(tokenDescription);
            if (tokenDescription.endsWith("token")) {
                sb.append("s");
            }
            int tokenLocation = sb.indexOf("token ");
            if (tokenLocation != -1) {
                sb.replace(tokenLocation, tokenLocation + 6, "tokens ");
            }
        }

        if (attacking) {
            if (singular) {
                sb.append(" that's");
            } else {
                sb.append(" that are");
            }
            if (tapped) {
                sb.append(" tapped and");
            }
            sb.append(" attacking");
        }

        String message = amount.getMessage();
        if (!message.isEmpty()) {
            if (amount.toString().equals("X")) {
                if (sb.toString().endsWith(".\"")) {
                    sb.replace(sb.length() - 2, sb.length(), ",\" where X is ");
                } else {
                    sb.append(", where X is ");
                }
            } else {
                sb.append(" for each ");
            }
        }
        sb.append(message);

        if (this.additionalRules != null) {
            sb.append(this.additionalRules);
        }

        staticText = sb.toString();
    }
}
