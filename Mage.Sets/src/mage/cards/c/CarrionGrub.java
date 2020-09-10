package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarrionGrub extends CardImpl {

    public CarrionGrub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Carrion Grub gets +X/+0, where X is the greatest power among creature cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                CarrionGrubValue.instance, StaticValue.get(0), Duration.WhileOnBattlefield
        )));

        // When Carrion Grub enters the battlefield, mill four cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(4)));
    }

    private CarrionGrub(final CarrionGrub card) {
        super(card);
    }

    @Override
    public CarrionGrub copy() {
        return new CarrionGrub(this);
    }
}

enum CarrionGrubValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return player.getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(MageObject::isCreature)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
    }

    @Override
    public CarrionGrubValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the greatest power among creature cards in your graveyard";
    }

    @Override
    public String toString() {
        return "X";
    }
}