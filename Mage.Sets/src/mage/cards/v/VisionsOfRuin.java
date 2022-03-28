package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.CommanderManaValueAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisionsOfRuin extends CardImpl {

    public VisionsOfRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Each opponent sacrifices an artifact. For each artifact sacrificed this way, you create a Treasure token.
        this.getSpellAbility().addEffect(new VisionsOfRuinEffect());

        // Flashback {8}{R}{R}. This spell costs {X} less to cast this way, where X is the greatest mana value of a commander you own on the battlefield or in the command zone.
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{8}{R}{R}"))
                .setAbilityName("This spell costs {X} less to cast this way, where X is the greatest mana value " +
                        "of a commander you own on the battlefield or in the command zone.")
                .setCostAdjuster(CommanderManaValueAdjuster.instance));
    }

    private VisionsOfRuin(final VisionsOfRuin card) {
        super(card);
    }

    @Override
    public VisionsOfRuin copy() {
        return new VisionsOfRuin(this);
    }
}

class VisionsOfRuinEffect extends OneShotEffect {

    VisionsOfRuinEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent sacrifices an artifact. For each artifact sacrificed this way, you create a Treasure token";
    }

    private VisionsOfRuinEffect(final VisionsOfRuinEffect effect) {
        super(effect);
    }

    @Override
    public VisionsOfRuinEffect copy() {
        return new VisionsOfRuinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> permanents = new HashSet<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null || game.getBattlefield().count(
                    StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                    playerId, source, game
            ) < 1) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT);
            target.setNotTarget(true);
            opponent.choose(Outcome.Sacrifice, target, source, game);
            permanents.add(game.getPermanent(target.getFirstTarget()));
        }
        int sacrificed = 0;
        for (Permanent permanent : permanents) {
            if (permanent != null && permanent.sacrifice(source, game)) {
                sacrificed++;
            }
        }
        if (sacrificed > 0) {
            new TreasureToken().putOntoBattlefield(sacrificed, game, source, source.getControllerId());
        }
        return true;
    }
}
