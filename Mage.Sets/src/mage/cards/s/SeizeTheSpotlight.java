package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeizeTheSpotlight extends CardImpl {

    public SeizeTheSpotlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Each opponent chooses fame or fortune. For each player who chose fame, gain control of a creature that player controls until end of turn. Untap those creatures and they gain haste until end of turn. For each player who chose fortune, you draw a card and create a Treasure token.
        this.getSpellAbility().addEffect(new SeizeTheSpotlightEffect());
    }

    private SeizeTheSpotlight(final SeizeTheSpotlight card) {
        super(card);
    }

    @Override
    public SeizeTheSpotlight copy() {
        return new SeizeTheSpotlight(this);
    }
}

class SeizeTheSpotlightEffect extends OneShotEffect {

    SeizeTheSpotlightEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent chooses fame or fortune. For each player who chose fame, " +
                "gain control of a creature that player controls until end of turn. " +
                "Untap those creatures and they gain haste until end of turn. " +
                "For each player who chose fortune, you draw a card and create a Treasure token";
    }

    private SeizeTheSpotlightEffect(final SeizeTheSpotlightEffect effect) {
        super(effect);
    }

    @Override
    public SeizeTheSpotlightEffect copy() {
        return new SeizeTheSpotlightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<Player> fame = new ArrayList<>();
        int fortune = 0;
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }
            boolean choseFame = opponent.chooseUse(Outcome.Detriment, "Choose fame (give a creature) or fortune (give a card and a treasure)", null, "Fame", "Fortune", source, game);
            game.informPlayers(opponent.getLogName() + " chooses " + (choseFame ? "fame" : "fortune"));
            if (choseFame) {
                fame.add(opponent);
            } else {
                fortune++;
            }
        }
        List<Permanent> permanents = new ArrayList<>();
        for (Player opponent : fame) {
            FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + opponent.getName());
            filter.add(new ControllerIdPredicate(opponent.getId()));
            TargetPermanent target = new TargetPermanent(filter);
            target.setNotTarget(true);
            if (!target.canChoose(source.getSourceId(), source, game)) {
                continue;
            }
            controller.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null) {
                continue;
            }
            permanent.untap(game);
            permanents.add(permanent);
        }
        if (!permanents.isEmpty()) {
            game.addEffect(new GainControlTargetEffect(Duration.EndOfTurn)
                    .setTargetPointer(new FixedTargets(permanents, game)), source);
            game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                    .setTargetPointer(new FixedTargets(permanents, game)), source);
        }
        if (fortune > 0) {
            controller.drawCards(fortune, source, game);
            new TreasureToken().putOntoBattlefield(fortune, game, source);
        }
        return true;
    }
}
