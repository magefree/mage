package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class OketrasLastMercy extends CardImpl {

    public OketrasLastMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // Your life total becomes your starting life total. Lands you control don't untap during your next untap phase.
        this.getSpellAbility().addEffect(new OketrasLastMercyEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersUntapStepAllEffect(
                Duration.UntilYourNextTurn, TargetController.YOU, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS));
    }

    private OketrasLastMercy(final OketrasLastMercy card) {
        super(card);
    }

    @Override
    public OketrasLastMercy copy() {
        return new OketrasLastMercy(this);
    }
}

class OketrasLastMercyEffect extends OneShotEffect {

    OketrasLastMercyEffect() {
        super(Outcome.Benefit);
        staticText = "Your life total becomes equal to your starting life total";
    }

    private OketrasLastMercyEffect(final OketrasLastMercyEffect effect) {
        super(effect);
    }

    @Override
    public OketrasLastMercyEffect copy() {
        return new OketrasLastMercyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.setLife(game.getStartingLife(), game, source);
            return true;
        }
        return false;
    }

}
