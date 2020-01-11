package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnemyOfEnlightenment extends CardImpl {

    public EnemyOfEnlightenment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Enemy of Enlightenment gets -1/-1 for each card in your opponents' hands.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                EnemyOfEnlightenmentValue.instance, EnemyOfEnlightenmentValue.instance, Duration.WhileOnBattlefield
        ).setText("{this} gets -1/-1 for each card in your opponents' hands")));

        // At the beginning of your upkeep, each player discards a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DiscardEachPlayerEffect(), TargetController.YOU, false
        ));
    }

    private EnemyOfEnlightenment(final EnemyOfEnlightenment card) {
        super(card);
    }

    @Override
    public EnemyOfEnlightenment copy() {
        return new EnemyOfEnlightenment(this);
    }
}

enum EnemyOfEnlightenmentValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return -1 * game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(game::getPlayer)
                .map(Player::getHand)
                .mapToInt(Set::size)
                .sum();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}