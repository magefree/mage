package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PitilessCarnage extends CardImpl {

    public PitilessCarnage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Sacrifice any number of permanents you control, then that many cards.
        this.getSpellAbility().addEffect(new PitilessCarnageEffect());

        // Plot {1}{B}{B}
        this.addAbility(new PlotAbility("{1}{B}{B}"));
    }

    private PitilessCarnage(final PitilessCarnage card) {
        super(card);
    }

    @Override
    public PitilessCarnage copy() {
        return new PitilessCarnage(this);
    }
}

class PitilessCarnageEffect extends OneShotEffect {

    PitilessCarnageEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice any number of permanents you control, then that many cards";
    }

    private PitilessCarnageEffect(final PitilessCarnageEffect effect) {
        super(effect);
    }

    @Override
    public PitilessCarnageEffect copy() {
        return new PitilessCarnageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_PERMANENTS
        );
        player.choose(outcome, target, source, game);
        Set<Permanent> permanents = target
                .getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            permanent.sacrifice(source, game);
        }
        player.drawCards(permanents.size(), source, game);
        return true;
    }
}
