package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 * Grants an evoke alternative cost to cards matching a filter.
 *
 * @author nandmp
 */
public class GainEvokeAbilityEffect extends ContinuousEffectImpl {

    private final EvokeAbility evokeAbility;

    public GainEvokeAbilityEffect(Cost evokeCost, FilterCard filter, Zone fromZone, String rule) {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.AddAbility);
        this.evokeAbility = new GrantedEvokeAbility(evokeCost, filter, fromZone);
        this.staticText = rule;
    }

    private GainEvokeAbilityEffect(final GainEvokeAbilityEffect effect) {
        super(effect);
        this.evokeAbility = effect.evokeAbility.copy();
    }

    @Override
    public GainEvokeAbilityEffect copy() {
        return new GainEvokeAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.getAlternativeSourceCosts().add(evokeAbility);
        return true;
    }

    private static class GrantedEvokeAbility extends EvokeAbility {

        private static final String GRANTED_EVOKE_ACTIVATION_KEY = "GrantedEvoke";

        private final FilterCard filter;
        private final Zone fromZone;

        private GrantedEvokeAbility(Cost cost, FilterCard filter, Zone fromZone) {
            super(cost, GRANTED_EVOKE_ACTIVATION_KEY);
            this.filter = filter;
            this.fromZone = fromZone;
        }

        private GrantedEvokeAbility(final GrantedEvokeAbility ability) {
            super(ability);
            this.filter = ability.filter.copy();
            this.fromZone = ability.fromZone;
        }

        @Override
        public GrantedEvokeAbility copy() {
            return new GrantedEvokeAbility(this);
        }

        @Override
        public boolean isAvailable(Ability source, Game game) {
            Card card = game.getCard(source.getSourceId());
            Spell spell = game.getStack().getSpell(source.getSourceId());
            boolean castFromCorrectZone = fromZone.match(game.getState().getZone(source.getSourceId()))
                    || spell != null && fromZone.match(spell.getFromZone());
            return card != null && castFromCorrectZone && filter.match(card, game);
        }

        @Override
        public boolean activateAlternativeCosts(Ability ability, Game game) {
            if (!super.activateAlternativeCosts(ability, game)) {
                return false;
            }
            addEvokeTriggeredAbility(game, ability);
            return true;
        }
    }
}
