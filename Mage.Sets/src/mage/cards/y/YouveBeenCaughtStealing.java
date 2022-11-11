package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouveBeenCaughtStealing extends CardImpl {

    public YouveBeenCaughtStealing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Choose one —
        // • Threaten the Merchant — Each creature blocks this turn if able.
        this.getSpellAbility().addEffect(new BlocksIfAbleAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
                .setText("each creature blocks this turn if able"));
        this.getSpellAbility().withFirstModeFlavorWord("Threaten the Merchant");

        // • Bribe the Guards — You create a Treasure token for each opponent who was dealt damage this turn.
        this.getSpellAbility().addMode(new Mode(new CreateTokenEffect(
                new TreasureToken(), YouveBeenCaughtStealingValue.instance
        ).setText("you create a Treasure token for each opponent who was dealt damage this turn")).withFlavorWord("Bribe the Guards"));
        this.getSpellAbility().addWatcher(new AmountOfDamageAPlayerReceivedThisTurnWatcher());
    }

    private YouveBeenCaughtStealing(final YouveBeenCaughtStealing card) {
        super(card);
    }

    @Override
    public YouveBeenCaughtStealing copy() {
        return new YouveBeenCaughtStealing(this);
    }
}

enum YouveBeenCaughtStealingValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(game
                        .getState()
                        .getWatcher(AmountOfDamageAPlayerReceivedThisTurnWatcher.class)
                        ::getAmountOfDamageReceivedThisTurn)
                .mapToInt(x -> Math.min(x, 1))
                .sum();
    }

    @Override
    public YouveBeenCaughtStealingValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "opponent who was dealt damage this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}
