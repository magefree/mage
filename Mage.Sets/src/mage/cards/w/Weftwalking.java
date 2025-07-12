package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ShuffleHandGraveyardIntoLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Weftwalking extends CardImpl {

    public Weftwalking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{U}");

        // When this enchantment enters, if you cast it, shuffle your hand and graveyard into your library, then draw seven cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ShuffleHandGraveyardIntoLibraryEffect())
                .withInterveningIf(CastFromEverywhereSourceCondition.instance);
        ability.addEffect(new DrawCardSourceControllerEffect(7).concatBy(", then"));
        this.addAbility(ability);

        // The first spell each player casts during each of their turns may be cast without paying its mana cost.
        this.addAbility(new SimpleStaticAbility(new WeftwalkingEffect()));
    }

    private Weftwalking(final Weftwalking card) {
        super(card);
    }

    @Override
    public Weftwalking copy() {
        return new Weftwalking(this);
    }
}

class WeftwalkingEffect extends ContinuousEffectImpl {

    private enum WeftwalkingCondition implements Condition {
        instance;

        @Override
        public boolean apply(Game game, Ability source) {
            return game
                    .getState()
                    .getWatcher(SpellsCastWatcher.class)
                    .getSpellsCastThisTurn(game.getActivePlayerId())
                    .isEmpty();
        }
    }

    private final AlternativeCostSourceAbility alternativeCastingCostAbility;

    WeftwalkingEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.PlayForFree);
        this.alternativeCastingCostAbility = new AlternativeCostSourceAbility(
                null, WeftwalkingCondition.instance, null, StaticFilters.FILTER_CARD_NON_LAND, true
        );
        staticText = "the first spell each player casts during each of their turns may be cast without paying its mana cost";
    }

    private WeftwalkingEffect(final WeftwalkingEffect effect) {
        super(effect);
        this.alternativeCastingCostAbility = effect.alternativeCastingCostAbility;
    }

    @Override
    public WeftwalkingEffect copy() {
        return new WeftwalkingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
        player.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        return true;
    }
}
