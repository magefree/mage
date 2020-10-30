package mage.cards.c;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author emerald000
 */
public final class CouncilsJudgment extends CardImpl {

    public CouncilsJudgment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Will of the council - Starting with you, each player votes for a nonland permanent you don't control. Exile each permanent with the most votes or tied for most votes.
        this.getSpellAbility().addEffect(new CouncilsJudgmentEffect());
    }

    public CouncilsJudgment(final CouncilsJudgment card) {
        super(card);
    }

    @Override
    public CouncilsJudgment copy() {
        return new CouncilsJudgment(this);
    }
}

class CouncilsJudgmentEffect extends OneShotEffect {

    CouncilsJudgmentEffect() {
        super(Outcome.Exile);
        this.staticText = "<i>Will of the council</i> &mdash; Starting with you, each player votes for a nonland permanent you don't control. Exile each permanent with the most votes or tied for most votes";
    }

    CouncilsJudgmentEffect(final CouncilsJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public CouncilsJudgmentEffect copy() {
        return new CouncilsJudgmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<Permanent, Integer> chosenCards = new HashMap<>(2);
            int maxCount = 0;
            FilterNonlandPermanent filter = new FilterNonlandPermanent("a nonland permanent " + controller.getLogName() + " doesn't control");
            filter.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
            //Players each choose a legal permanent
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Target target = new TargetNonlandPermanent(filter);
                    target.setNotTarget(true);
                    if (player.choose(Outcome.Exile, target, source.getSourceId(), game)) {
                        Permanent permanent = game.getPermanent(target.getFirstTarget());
                        if (permanent != null) {
                            if (chosenCards.containsKey(permanent)) {
                                int count = chosenCards.get(permanent) + 1;
                                if (count > maxCount) {
                                    maxCount = count;
                                }
                                chosenCards.put(permanent, count);
                            } else {
                                if (maxCount == 0) {
                                    maxCount = 1;
                                }
                                chosenCards.put(permanent, 1);
                            }
                            game.informPlayers(player.getLogName() + " has chosen: " + permanent.getLogName());
                        }
                    }
                }
            }

            //Exile the card(s) with the most votes.
            for (Entry<Permanent, Integer> entry : chosenCards.entrySet()) {
                if (entry.getValue() == maxCount) {
                    Permanent permanent = entry.getKey();
                    controller.moveCardToExileWithInfo(permanent, null, "", source.getSourceId(), game, Zone.BATTLEFIELD, true);
                }
            }
            return true;
        }
        return false;
    }
}
