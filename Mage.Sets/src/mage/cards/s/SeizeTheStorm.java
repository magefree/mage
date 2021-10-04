package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SeizeTheStormToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeizeTheStorm extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Spells in your graveyard and flashback cards in exile", SeizeTheStormValue.instance
    );

    public SeizeTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Create a red Elemental creature token with trample and "This creature's power and toughness are each equal to the number of instant and sorcery cards in your graveyard, plus the number of cards with flashback you own in exile."
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new SeizeTheStormToken(SeizeTheStormValue.instance, hint)
        ));
        this.getSpellAbility().addHint(hint);

        // Flashback {6}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{R}")));
    }

    private SeizeTheStorm(final SeizeTheStorm card) {
        super(card);
    }

    @Override
    public SeizeTheStorm copy() {
        return new SeizeTheStorm(this);
    }
}

enum SeizeTheStormValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return player.getGraveyard().count(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game
        ) + game.getExile()
                .getAllCards(game, sourceAbility.getControllerId())
                .stream()
                .filter(card -> card.getAbilities(game).containsClass(FlashbackAbility.class))
                .mapToInt(x -> 1).sum();
    }

    @Override
    public SeizeTheStormValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "instant and sorcery cards in your graveyard, plus the number of cards with flashback you own in exile";
    }
}
