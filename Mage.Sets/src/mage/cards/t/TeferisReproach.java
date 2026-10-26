package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.abilities.keyword.ProtectionFromEverythingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TeferisReproach extends CardImpl {

    public TeferisReproach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Choose target opponent. Until that player's next turn, they gain protection from everything and their life total can't change. All nonland permanents they control phase out.
        this.getSpellAbility().addEffect(new TeferisReproachEffect());
        this.getSpellAbility().addEffect(new TeferisReproachPhaseOutEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Exile Teferi's Reproach.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private TeferisReproach(final TeferisReproach card) {
        super(card);
    }

    @Override
    public TeferisReproach copy() {
        return new TeferisReproach(this);
    }
}

class TeferisReproachEffect extends ContinuousEffectImpl {

    TeferisReproachEffect() {
        super(Duration.UntilYourNextTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "choose target opponent. Until that player's next turn, "
                + "they gain protection from everything and their life total can't change";
    }

    private TeferisReproachEffect(final TeferisReproachEffect effect) {
        super(effect);
    }

    @Override
    public TeferisReproachEffect copy() {
        return new TeferisReproachEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        UUID playerId = getTargetPointer().getFirst(game, source);
        if (playerId == null) {
            discard();
            return;
        }
        // duration ends on the target player's next turn, not the caster's
        setStartingControllerAndTurnNum(game, playerId, game.getActivePlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getStartingController());
        if (player == null) {
            discard();
            return false;
        }
        player.addAbility(new ProtectionFromEverythingAbility());
        player.setLifeTotalCanChange(false);
        return true;
    }
}

class TeferisReproachPhaseOutEffect extends OneShotEffect {

    TeferisReproachPhaseOutEffect() {
        super(Outcome.Detriment);
        staticText = "All nonland permanents they control phase out. <i>(While they're phased out, "
                + "they're treated as though they don't exist. They phase in before that player "
                + "untaps during their next untap step.)</i><br>";
    }

    private TeferisReproachPhaseOutEffect(final TeferisReproachPhaseOutEffect effect) {
        super(effect);
    }

    @Override
    public TeferisReproachPhaseOutEffect copy() {
        return new TeferisReproachPhaseOutEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        List<UUID> permIds = game
                .getBattlefield()
                .getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND, player.getId(), source, game)
                .stream()
                .map(Permanent::getId)
                .collect(Collectors.toList());
        return new PhaseOutAllEffect(permIds).apply(game, source);
    }
}
