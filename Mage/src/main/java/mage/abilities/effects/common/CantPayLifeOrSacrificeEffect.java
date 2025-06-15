package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SpellOrActivatedAbilitySourcePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * Effect used to prevent sacrificing or paying life as a cost for activated abilities and casting spells.
 * @author Jmlundeen
 */
public class CantPayLifeOrSacrificeEffect extends ContinuousEffectImpl {

    private final FilterPermanent filter;

    /**
     * @param filter {@link FilterPermanent} with message set as a descriptor e.g. "creatures" or "nonland permanents"
     */
    public CantPayLifeOrSacrificeEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        this.filter = filter;
        filter.add(SpellOrActivatedAbilitySourcePredicate.instance);
        this.staticText = "Players can't pay life or sacrifice " + filter.getMessage() + " to cast spells or activate abilities";
    }

    private CantPayLifeOrSacrificeEffect(CantPayLifeOrSacrificeEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    public CantPayLifeOrSacrificeEffect copy() {
        return new CantPayLifeOrSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            player.setPayLifeCostLevel(Player.PayLifeCostLevel.nonSpellnonActivatedAbilities);
            player.setCanPaySacrificeCostFilter(filter);
        }
        return true;
    }
}
