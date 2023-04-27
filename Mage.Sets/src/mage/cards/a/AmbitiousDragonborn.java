package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author TheElk801
 */
public final class AmbitiousDragonborn extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Greatest power among creatures you control and in your graveyard", AmbitiousDragonbornValue.instance
    );

    public AmbitiousDragonborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ambitious Dragonborn enters the battlefield with X +1/+1 counters on it, where X is the greatest power among creatures you control and creature cards in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), AmbitiousDragonbornValue.instance, false
        ), "with X +1/+1 counters on it, where X is the greatest power " +
                "among creatures you control and creature cards in your graveyard"));
    }

    private AmbitiousDragonborn(final AmbitiousDragonborn card) {
        super(card);
    }

    @Override
    public AmbitiousDragonborn copy() {
        return new AmbitiousDragonborn(this);
    }
}

enum AmbitiousDragonbornValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return Stream.concat(
                player.getGraveyard()
                        .getCards(StaticFilters.FILTER_CARD_CREATURE, game)
                        .stream(),
                game.getBattlefield()
                        .getActivePermanents(
                                StaticFilters.FILTER_CONTROLLED_CREATURE,
                                sourceAbility.getControllerId(), sourceAbility, game
                        ).stream()
        ).map(MageObject::getPower).mapToInt(MageInt::getValue).max().orElse(0);
    }

    @Override
    public AmbitiousDragonbornValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
