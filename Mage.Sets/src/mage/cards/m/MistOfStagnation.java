package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class MistOfStagnation extends CardImpl {

    public MistOfStagnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Permanents don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, StaticFilters.FILTER_PERMANENTS)));

        // At the beginning of each player's upkeep, that player chooses a permanent for each card in their graveyard, then untaps those permanents.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MistOfStagnationEffect(), TargetController.ANY, false));
    }

    private MistOfStagnation(final MistOfStagnation card) {
        super(card);
    }

    @Override
    public MistOfStagnation copy() {
        return new MistOfStagnation(this);
    }
}

class MistOfStagnationEffect extends OneShotEffect {

    MistOfStagnationEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player chooses a permanent for each card in their graveyard, then untaps those permanents";
    }

    private MistOfStagnationEffect(final MistOfStagnationEffect effect) {
        super(effect);
    }

    @Override
    public MistOfStagnationEffect copy() {
        return new MistOfStagnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer != null) {
            int cardsInGrave = activePlayer.getGraveyard().size();
            if (cardsInGrave > 0) {
                TargetPermanent target = new TargetPermanent(cardsInGrave, cardsInGrave, new FilterPermanent("permanents to untap"), true);
                activePlayer.chooseTarget(outcome, target, source, game);
                for (UUID oneTarget : target.getTargets()) {
                    Permanent p = game.getPermanent(oneTarget);
                    if (p != null) {
                        p.untap(game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
