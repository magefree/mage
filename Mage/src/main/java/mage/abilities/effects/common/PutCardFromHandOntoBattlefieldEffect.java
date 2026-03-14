package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.Optional;

/**
 * @author magenoxx_at_gmail.com
 */
public class PutCardFromHandOntoBattlefieldEffect extends OneShotEffect {

    private final FilterCard filter;
    private final boolean useTargetController;
    private final boolean tapped;
    private final boolean attacking;

    public PutCardFromHandOntoBattlefieldEffect() {
        this(StaticFilters.FILTER_CARD_A_PERMANENT, false);
    }

    public PutCardFromHandOntoBattlefieldEffect(FilterCard filter) {
        this(filter, false);
    }

    public PutCardFromHandOntoBattlefieldEffect(FilterCard filter, boolean useTargetController) {
        this(filter, useTargetController, false);
    }

    public PutCardFromHandOntoBattlefieldEffect(FilterCard filter, boolean useTargetController, boolean tapped) {
        this(filter, useTargetController, tapped, false);
    }

    public PutCardFromHandOntoBattlefieldEffect(FilterCard filter, boolean useTargetController, boolean tapped, boolean attacking) {
        super(Outcome.PutCardInPlay);
        this.filter = filter;
        this.useTargetController = useTargetController;
        this.tapped = tapped;
        this.attacking = attacking;
    }

    protected PutCardFromHandOntoBattlefieldEffect(final PutCardFromHandOntoBattlefieldEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.useTargetController = effect.useTargetController;
        this.tapped = effect.tapped;
        this.attacking = effect.attacking;
    }

    @Override
    public PutCardFromHandOntoBattlefieldEffect copy() {
        return new PutCardFromHandOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player;
        if (useTargetController) {
            player = game.getPlayer(getTargetPointer().getFirst(game, source));
        } else {
            player = game.getPlayer(source.getControllerId());
        }
        if (player == null) {
            return false;
        }
        if (!player.chooseUse(Outcome.PutCardInPlay, "Put " + filter.getMessage() + " from your hand onto the battlefield?", source, game)) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(filter);
        player.choose(Outcome.PutCardInPlay, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null || !player.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, false, null)) {
            return false;
        }
        if (attacking) {
            Optional.ofNullable(CardUtil.getPermanentFromCardPutToBattlefield(card, game))
                    .ifPresent(permanent -> game.getCombat().addAttackingCreature(permanent.getId(), game));
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (this.staticText != null && !this.staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (useTargetController) {
            sb.append("that player");
        } else {
            sb.append("you");
        }
        sb.append(" may put ");
        sb.append(CardUtil.addArticle(filter.getMessage()));
        sb.append(" from ");
        if (useTargetController) {
            sb.append("their");
        } else {
            sb.append("your");
        }
        sb.append(" hand onto the battlefield");
        if (tapped) {
            sb.append(" tapped");
        }
        if (attacking) {
            sb.append(" and attacking");
        }
        return sb.toString();
    }
}
