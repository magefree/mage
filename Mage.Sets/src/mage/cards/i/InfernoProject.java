package mage.cards.i;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfernoProject extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Total mana value of instants and sorceries in your graveyard", InfernoProjectValue.instance
    );

    public InfernoProject(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Inferno Project enters the battlefield with X +1/+1 counters on it, where X is the total mana value of instant and sorcery cards in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), InfernoProjectValue.instance, false
        ), "with X +1/+1 counters on it, where X is the total mana value " +
                "of instant and sorcery cards in your graveyard").addHint(hint));
    }

    private InfernoProject(final InfernoProject card) {
        super(card);
    }

    @Override
    public InfernoProject copy() {
        return new InfernoProject(this);
    }
}

enum InfernoProjectValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return player
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.isInstantOrSorcery(game))
                .mapToInt(MageObject::getManaValue)
                .sum();
    }

    @Override
    public InfernoProjectValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
