package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.UntapAllDuringEachOtherPlayersUntapStepEffect;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;

public class TeferiWhoSlowsTheSunsetEmblem extends Emblem {
    // You get an emblem with "Untap all permanents you control during each opponent's untap step" and "You draw a card during each opponent's draw step."
    public TeferiWhoSlowsTheSunsetEmblem() {
        this.setName("Emblem Teferi");
        this.getAbilities().add(new SimpleStaticAbility(
                Zone.COMMAND, new UntapAllDuringEachOtherPlayersUntapStepEffect(StaticFilters.FILTER_CONTROLLED_PERMANENTS)
        ));
        this.getAbilities().add(new SimpleStaticAbility(new TeferiWhoSlowsTheSunsetEmblemEffect()));

        this.setExpansionSetCodeForImage("MID");
    }
}

class TeferiWhoSlowsTheSunsetEmblemEffect extends ContinuousEffectImpl {

    TeferiWhoSlowsTheSunsetEmblemEffect() {
        super(Duration.EndOfGame, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "you draw a card during each opponent's draw step";
    }

    private TeferiWhoSlowsTheSunsetEmblemEffect(final TeferiWhoSlowsTheSunsetEmblemEffect effect) {
        super(effect);
    }

    @Override
    public TeferiWhoSlowsTheSunsetEmblemEffect copy() {
        return new TeferiWhoSlowsTheSunsetEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.setDrawsOnOpponentsTurn(true);
        return true;
    }
}
