package mage.abilities.effects.common.continuous;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.costs.AlternativeSourceCosts;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author Styxo
 */
public class GainAbilityControlledSpellsEffect extends ContinuousEffectImpl {

    private final Ability ability;
    private final FilterCard filter;
    private final AlternativeSourceCosts alternativeSourceCosts;

    public GainAbilityControlledSpellsEffect(Ability ability, FilterNonlandCard filter) {
        this(ability, (FilterCard) filter);
    }

    public GainAbilityControlledSpellsEffect(Ability ability, FilterCard filter) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.filter = filter;
        this.alternativeSourceCosts = ability instanceof AlternativeSourceCosts
                ? new FilteredAlternativeSourceCostsAbility((AlternativeSourceCosts) ability, filter)
                : null;
        staticText = filter.getMessage() + " have " + CardUtil.getTextWithFirstCharLowerCase(CardUtil.stripReminderText(ability.getRule()));
    }

    private GainAbilityControlledSpellsEffect(final GainAbilityControlledSpellsEffect effect) {
        super(effect);
        this.ability = effect.ability;
        this.filter = effect.filter;
        this.alternativeSourceCosts = effect.alternativeSourceCosts == null
                ? null
                : (AlternativeSourceCosts) effect.alternativeSourceCosts.copy();
    }

    @Override
    public GainAbilityControlledSpellsEffect copy() {
        return new GainAbilityControlledSpellsEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (layer == Layer.RulesEffects) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null || alternativeSourceCosts == null) {
                return false;
            }
            AlternativeSourceCosts costs = (AlternativeSourceCosts) alternativeSourceCosts.copy();
            costs.setSourceId(source.getSourceId());
            costs.setControllerId(source.getControllerId());
            player.getAlternativeSourceCosts().add(costs);
            return true;
        }
        return apply(game, source);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return super.hasLayer(layer) || (alternativeSourceCosts != null && layer == Layer.RulesEffects);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        for (Card card : game.getExile().getCardsInRange(game, source.getControllerId())) {
            if (filter.match(card, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getLibrary().getCards(game)) {
            if (filter.match(card, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getHand().getCards(game)) {
            if (filter.match(card, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        for (Card card : player.getGraveyard().getCards(game)) {
            if (filter.match(card, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }

        // workaround to gain cost reduction abilities to commanders before cast (make it playable)
        game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY)
                .stream()
                .filter(card -> filter.match(card, player.getId(), source, game))
                .forEach(card -> game.getState().addOtherAbility(card, ability));

        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof Spell) || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            // TODO: Distinguish "you cast" to exclude copies
            Card card = game.getCard(stackObject.getSourceId());
            if (card != null && filter.match((Spell) stackObject, player.getId(), source, game)) {
                game.getState().addOtherAbility(card, ability);
            }
        }
        return true;
    }

    private static final class FilteredAlternativeSourceCostsAbility extends StaticAbility implements AlternativeSourceCosts {

        private final AlternativeSourceCosts alternativeSourceCosts;
        private final FilterCard filter;

        private FilteredAlternativeSourceCostsAbility(AlternativeSourceCosts alternativeSourceCosts, FilterCard filter) {
            super(Zone.ALL, null);
            this.alternativeSourceCosts = (AlternativeSourceCosts) alternativeSourceCosts.copy();
            this.filter = filter.copy();
        }

        private FilteredAlternativeSourceCostsAbility(final FilteredAlternativeSourceCostsAbility ability) {
            super(ability);
            this.alternativeSourceCosts = (AlternativeSourceCosts) ability.alternativeSourceCosts.copy();
            this.filter = ability.filter.copy();
        }

        @Override
        public FilteredAlternativeSourceCostsAbility copy() {
            return new FilteredAlternativeSourceCostsAbility(this);
        }

        private boolean prepareFor(Ability ability, Game game) {
            Spell spell = game.getStack().getSpell(ability.getSourceId());
            if (spell != null && !filter.match(spell, ability.getControllerId(), ability, game)) {
                return false;
            }
            Card card = game.getCard(ability.getSourceId());
            if (spell == null && (card == null || !filter.match(card, ability.getControllerId(), ability, game))) {
                return false;
            }
            alternativeSourceCosts.setSourceId(ability.getSourceId());
            alternativeSourceCosts.setControllerId(ability.getControllerId());
            this.setSourceId(ability.getSourceId());
            this.setControllerId(ability.getControllerId());
            return true;
        }

        @Override
        public MageIdentifier getIdentifier() {
            return alternativeSourceCosts.getIdentifier();
        }

        @Override
        public boolean canActivateAlternativeCostsNow(Ability ability, Game game) {
            return prepareFor(ability, game) && alternativeSourceCosts.canActivateAlternativeCostsNow(ability, game);
        }

        @Override
        public String getAlternativeCostText(Ability ability, Game game) {
            if (!prepareFor(ability, game)) {
                return "";
            }
            return alternativeSourceCosts.getAlternativeCostText(ability, game);
        }

        @Override
        public boolean activateAlternativeCosts(Ability ability, Game game) {
            return prepareFor(ability, game) && alternativeSourceCosts.activateAlternativeCosts(ability, game);
        }

        @Override
        public boolean isAvailable(Ability source, Game game) {
            return prepareFor(source, game) && alternativeSourceCosts.isAvailable(source, game);
        }

        @Override
        public Costs<Cost> getCosts() {
            return alternativeSourceCosts.getCosts();
        }

        @Override
        public boolean isActivated(Ability source, Game game) {
            return alternativeSourceCosts.isActivated(source, game);
        }

        @Override
        public String getCastMessageSuffix(Game game) {
            return alternativeSourceCosts.getCastMessageSuffix(game);
        }

        @Override
        public void resetCost() {
            alternativeSourceCosts.resetCost();
        }
    }
}
