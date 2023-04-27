
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author JRHerlehy
 *         Created on 4/4/18.
 */
public class WUBRGInsteadEffect extends ContinuousEffectImpl {

    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{W}{U}{B}{R}{G}"), SourceIsSpellCondition.instance);

    public WUBRGInsteadEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may pay {W}{U}{B}{R}{G} rather than pay the mana cost for spells that you cast";
    }

    public WUBRGInsteadEffect(final WUBRGInsteadEffect effect) {
        super(effect);
    }

    @Override
    public WUBRGInsteadEffect copy() {
        return new WUBRGInsteadEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }

}
