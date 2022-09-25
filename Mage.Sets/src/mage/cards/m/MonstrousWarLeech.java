package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author TheElk801
 */
public final class MonstrousWarLeech extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Highest mana value in your graveyard", MonstrousWarLeechValue.instance
    );

    public MonstrousWarLeech(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.LEECH);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Kicker {U}
        this.addAbility(new KickerAbility("{U}"));

        // As Monstrous War-Leech enters the battlefield, if it was kicked, mill four cards.
        this.addAbility(new EntersBattlefieldAbility(
                new MillCardsControllerEffect(4), KickedCondition.ONCE,
                "As {this} enters the battlefield, if it was kicked, mill four cards.", ""
        ));

        // Monstrous War-Leech's power and toughness are each equal to the highest mana value among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(
                        MonstrousWarLeechValue.instance, Duration.EndOfGame
                )
        ));
    }

    private MonstrousWarLeech(final MonstrousWarLeech card) {
        super(card);
    }

    @Override
    public MonstrousWarLeech copy() {
        return new MonstrousWarLeech(this);
    }
}

enum MonstrousWarLeechValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional
                .ofNullable(game.getPlayer(sourceAbility.getControllerId()))
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(graveyard -> graveyard.getCards(game))
                .map(Collection::stream)
                .map(s -> s.mapToInt(MageObject::getManaValue))
                .map(IntStream::max)
                .map(x -> x.orElse(0))
                .orElse(0);
    }

    @Override
    public MonstrousWarLeechValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the highest mana value among cards in your graveyard";
    }

    @Override
    public String toString() {
        return "";
    }
}
