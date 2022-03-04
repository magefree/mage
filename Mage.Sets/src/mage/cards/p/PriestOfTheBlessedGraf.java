package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PriestOfTheBlessedGraf extends CardImpl {

    public PriestOfTheBlessedGraf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, create X 1/1 white Spirit creature tokens with flying, where X is the number of opponents who control more lands than you.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(
                new SpiritWhiteToken(), PriestOfTheBlessedGrafValue.instance
        ), TargetController.YOU, false).addHint(PriestOfTheBlessedGrafHint.instance));
    }

    private PriestOfTheBlessedGraf(final PriestOfTheBlessedGraf card) {
        super(card);
    }

    @Override
    public PriestOfTheBlessedGraf copy() {
        return new PriestOfTheBlessedGraf(this);
    }
}

enum PriestOfTheBlessedGrafValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return makeStream(game, sourceAbility).size();
    }

    @Override
    public PriestOfTheBlessedGrafValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of opponents who control more lands than you";
    }

    @Override
    public String toString() {
        return "X";
    }

    static List<String> makeStream(Game game, Ability source) {
        Map<UUID, Integer> map = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_LAND,
                        source.getControllerId(),
                        source.getSourceId(), game
                ).stream()
                .map(Controllable::getControllerId)
                .collect(Collectors.toMap(Function.identity(), u -> 1, Integer::sum));
        int myLands = map.getOrDefault(source.getControllerId(), 0);
        return game.getOpponents(source.getControllerId())
                .stream()
                .filter(playerId -> map.getOrDefault(playerId, 0) > myLands)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getName)
                .collect(Collectors.toList());
    }
}

enum PriestOfTheBlessedGrafHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        List<String> names = PriestOfTheBlessedGrafValue.makeStream(game, ability);
        return "Players who control more lands than you: " + names.size()
                + (names.size() > 0 ? " (" + String.join(", ", names) + ')' : "");
    }

    @Override
    public PriestOfTheBlessedGrafHint copy() {
        return this;
    }
}
