package mage.cards.n;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class NighthawkScavenger extends CardImpl {

    public NighthawkScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Nighthawk Scavenger's power is equal to 1 plus the number of card types among cards in your opponents' graveyards.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetPowerSourceEffect(
                        NighthawkScavengerValue.instance, Duration.EndOfGame
                ).setText("{this}'s power is equal to 1 plus the number of card types among cards in your opponents' graveyards.")
        ).addHint(NighthawkScavengerHint.instance));
    }

    private NighthawkScavenger(final NighthawkScavenger card) {
        super(card);
    }

    @Override
    public NighthawkScavenger copy() {
        return new NighthawkScavenger(this);
    }
}

enum NighthawkScavengerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return 1 + game.getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(MageObject::getCardType)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
                .size();
    }

    @Override
    public NighthawkScavengerValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum NighthawkScavengerHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        List<String> types = game
                .getOpponents(ability.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(graveyard -> graveyard.getCards(game))
                .flatMap(Collection::stream)
                .map(MageObject::getCardType)
                .flatMap(Collection::stream)
                .distinct()
                .map(CardType::toString)
                .sorted()
                .collect(Collectors.toList());
        String message = "" + types.size();
        if (types.size() > 0) {
            message += " (";
            message += types.stream().reduce((a, b) -> a + ", " + b).orElse("");
            message += ')';
        }
        return "Card types in opponents' graveyards: " + message;
    }

    @Override
    public NighthawkScavengerHint copy() {
        return instance;
    }
}
