package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
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

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SWAMP);

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
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            player.loseLife(game.getBattlefield().count(
                    filter, playerId, source.getSourceId(), game
            ), game, source, false);
        }
        return true;
    }
}
