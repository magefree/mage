package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

import java.util.*;

/**
 * @author TheElk801
 */
public final class FandanielTelophoroiAscian extends CardImpl {

    public FandanielTelophoroiAscian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Whenever you cast an instant or sorcery spell, surveil 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SurveilEffect(1), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // At the beginning of your end step, each opponent may sacrifice a nontoken creature of their choice. Each opponent who doesn't loses 2 life for each instant and sorcery card in your graveyard.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new FandanielTelophoroiAscianEffect()));
    }

    private FandanielTelophoroiAscian(final FandanielTelophoroiAscian card) {
        super(card);
    }

    @Override
    public FandanielTelophoroiAscian copy() {
        return new FandanielTelophoroiAscian(this);
    }
}

class FandanielTelophoroiAscianEffect extends OneShotEffect {

    FandanielTelophoroiAscianEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent may sacrifice a nontoken creature of their choice. " +
                "Each opponent who doesn't loses 2 life for each instant and sorcery card in your graveyard";
    }

    private FandanielTelophoroiAscianEffect(final FandanielTelophoroiAscianEffect effect) {
        super(effect);
    }

    @Override
    public FandanielTelophoroiAscianEffect copy() {
        return new FandanielTelophoroiAscianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> players = new HashSet<>();
        List<Permanent> permanents = new ArrayList<>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null || !game.getBattlefield().contains(
                    StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN,
                    playerId, source, game, 1
            )) {
                continue;
            }
            TargetSacrifice target = new TargetSacrifice(
                    0, 1, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
            );
            player.choose(Outcome.Sacrifice, target, source, game);
            permanents.add(game.getPermanent(target.getFirstTarget()));
            players.add(playerId);
        }
        permanents.removeIf(Objects::isNull);
        for (Permanent permanent : permanents) {
            if (permanent.sacrifice(source, game)) {
                players.remove(permanent.getControllerId());
            }
        }
        int count = Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getGraveyard)
                .map(g -> 2 * g.count(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game))
                .orElse(0);
        if (count < 1) {
            return true;
        }
        for (UUID playerId : players) {
            Optional.ofNullable(playerId)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.loseLife(count, game, source, false));
        }
        return true;
    }
}
