package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SharesCardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class BraidsArisenNightmare extends CardImpl {

    public BraidsArisenNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, you may sacrifice an artifact, creature, enchantment, land, or planeswalker.
        // If you do, each opponent may sacrifice a permanent that shares a card type with it.
        // For each opponent who doesn't, that player loses 2 life and you draw a card.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new BraidsArisenNightmareEffect(), true));
    }

    private BraidsArisenNightmare(final BraidsArisenNightmare card) {
        super(card);
    }

    @Override
    public BraidsArisenNightmare copy() {
        return new BraidsArisenNightmare(this);
    }
}

class BraidsArisenNightmareEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact, creature, enchantment, land, or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public BraidsArisenNightmareEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "you may sacrifice an artifact, creature, enchantment, land, or planeswalker. " +
                "If you do, each opponent may sacrifice a permanent that shares a card type with it. " +
                "For each opponent who doesn't, that player loses 2 life and you draw a card";
    }

    private BraidsArisenNightmareEffect(final BraidsArisenNightmareEffect effect) {
        super(effect);
    }

    @Override
    public BraidsArisenNightmareEffect copy() {
        return new BraidsArisenNightmareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
        if (!target.canChoose(controller.getId(), source, game)) {
            return false;
        }
        controller.chooseTarget(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        SharesCardTypePredicate predicate = new SharesCardTypePredicate(permanent.getCardType(game));
        FilterControlledPermanent opponentFilter = new FilterControlledPermanent(predicate.toString());
        opponentFilter.add(predicate);
        if (!permanent.sacrifice(source, game)) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(controller.getId(), true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            if (!braidsSacrifice(opponent, opponentFilter, game, source)) {
                opponent.loseLife(2, game, source, false);
                controller.drawCards(1, source, game);
            }
        }
        return true;
    }

    private boolean braidsSacrifice(Player opponent, FilterControlledPermanent opponentFilter, Game game, Ability source) {
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, opponentFilter, true);
        if (!target.canChoose(opponent.getId(), source, game)) {
            return false;
        }
        if (!opponent.chooseUse(Outcome.Sacrifice, "Sacrifice " + CardUtil.addArticle(opponentFilter.getMessage()) + '?', source, game)) {
            return false;
        }
        opponent.chooseTarget(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.sacrifice(source, game);
    }
}
