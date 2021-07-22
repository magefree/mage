
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class SternJudge extends CardImpl {

    public SternJudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Each player loses 1 life for each Swamp they control.
        this.addAbility(new SimpleActivatedAbility(new SternJudgeEffect(), new TapSourceCost()));
    }

    private SternJudge(final SternJudge card) {
        super(card);
    }

    @Override
    public SternJudge copy() {
        return new SternJudge(this);
    }
}

class SternJudgeEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    SternJudgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player loses 1 life for each Swamp they control.";
    }

    SternJudgeEffect(final SternJudgeEffect effect) {
        super(effect);
    }

    @Override
    public SternJudgeEffect copy() {
        return new SternJudgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getSourceId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int lifeToLose = game.getBattlefield().getAllActivePermanents(filter, playerId, game).size();
                player.loseLife(lifeToLose, game, source, false);
            }
        }
        return true;
    }
}
