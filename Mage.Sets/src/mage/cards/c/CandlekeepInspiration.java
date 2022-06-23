package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.AdventureCard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author TheElk801
 */
public final class CandlekeepInspiration extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Instants, sorceries, and Adventures in your graveyard and exile", CandlekeepInspirationValue.instance
    );

    public CandlekeepInspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Until end of turn, creatures you control have base power and toughness X/X, where X is the number of cards you own in exile and in your graveyard that are instant cards, are sorcery cards, and/or have an Adventure.
        this.getSpellAbility().addEffect(new SetPowerToughnessAllEffect(
                CandlekeepInspirationValue.instance, CandlekeepInspirationValue.instance,
                Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE, true
        ).setText("until end of turn, creatures you control have base power and toughness X/X, " +
                "where X is the number of cards you own in exile and in your graveyard " +
                "that are instant cards, are sorcery cards, and/or have an Adventure"));
        this.getSpellAbility().addHint(hint);
    }

    private CandlekeepInspiration(final CandlekeepInspiration card) {
        super(card);
    }

    @Override
    public CandlekeepInspiration copy() {
        return new CandlekeepInspiration(this);
    }
}

enum CandlekeepInspirationValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return Stream.concat(
                player.getGraveyard()
                        .getCards(game)
                        .stream(),
                game.getExile()
                        .getAllCards(game, sourceAbility.getControllerId())
                        .stream()
        )
                .filter(Objects::nonNull)
                .filter(card -> card.isInstantOrSorcery(game)
                        || card instanceof AdventureCard)
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public CandlekeepInspirationValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
