package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class PutCardIntoPlayWithHasteAndSacrificeEffect extends OneShotEffect {

    private final FilterCard filter;
    private final Duration duration;

    public PutCardIntoPlayWithHasteAndSacrificeEffect(FilterCard filter) {
        this(filter, Duration.Custom);
    }

    public PutCardIntoPlayWithHasteAndSacrificeEffect(FilterCard filter, Duration duration) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.duration = duration;
        this.staticText = "you may put " + CardUtil.addArticle(filter.getMessage()) +
                (" from your hand onto the battlefield. It gains haste " + duration).trim() +
                ". Sacrifice that " + filter.getMessage() + " at the beginning of the next end step";
    }

    private PutCardIntoPlayWithHasteAndSacrificeEffect(final PutCardIntoPlayWithHasteAndSacrificeEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.duration = effect.duration;
    }

    @Override
    public PutCardIntoPlayWithHasteAndSacrificeEffect copy() {
        return new PutCardIntoPlayWithHasteAndSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, 1, filter);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(CardUtil.getDefaultCardSideForBattlefield(game, card).getId());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), duration)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice it")
                        .setTargetPointer(new FixedTarget(permanent, game)),
                TargetController.ANY
        ), source);
        return true;
    }
}
