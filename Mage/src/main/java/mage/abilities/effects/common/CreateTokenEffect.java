
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
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

    private Token token;
    private DynamicValue amount;
    private boolean tapped;
    private boolean attacking;
    private UUID lastAddedTokenId;
    private List<UUID> lastAddedTokenIds = new ArrayList<>();

    public CreateTokenEffect(Token token) {
        this(token, StaticValue.get(1));
    }

    public CreateTokenEffect(Token token, int amount) {
        this(token, StaticValue.get(amount));
    }

    public CreateTokenEffect(Token token, DynamicValue amount) {
        this(token, amount, false, false);
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

    public CreateTokenEffect(final CreateTokenEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.token = effect.token.copy();
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
        this.lastAddedTokenId = effect.lastAddedTokenId;
        this.lastAddedTokenIds.addAll(effect.lastAddedTokenIds);
    }

    @Override
    public CreateTokenEffect copy() {
        return new CreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = amount.calculate(game, source, this);
        token.putOntoBattlefield(value, game, source, source.getControllerId(), tapped, attacking);
        this.lastAddedTokenId = token.getLastAddedToken();
        this.lastAddedTokenIds = token.getLastAddedTokenIds();

        return true;
    }

    public UUID getLastAddedTokenId() {
        return lastAddedTokenId;
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

    private void setText() {
        if (token.getDescription().contains(", a legendary")) {
            staticText = "create " + token.getDescription();
            return;
        }
        StringBuilder sb = new StringBuilder("create ");
        if (amount.toString().equals("1")) {
            if (tapped && !attacking) {
                sb.append("a tapped ");
                sb.append(token.getDescription());
            } else {
                sb.append(CardUtil.addArticle(token.getDescription()));
            }
        } else {
            sb.append(CardUtil.numberToText(amount.toString())).append(' ');
            if (tapped && !attacking) {
                sb.append("tapped ");
            }
            sb.append(token.getDescription());
            if (token.getDescription().endsWith("token")) {
                sb.append("s");
            }
            int tokenLocation = sb.indexOf("token ");
            if (tokenLocation != -1) {
                sb.replace(tokenLocation, tokenLocation + 6, "tokens ");
            }
        }
        if (attacking) {
            if (amount.toString().equals("1")) {
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
                sb.append(", where X is ");
            } else {
                sb.append(" for each ");
            }
        }
        sb.append(message);
        staticText = sb.toString();
    }
}
