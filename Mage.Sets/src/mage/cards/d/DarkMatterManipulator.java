package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class DarkMatterManipulator extends CardImpl {

    public DarkMatterManipulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When this creature enters, mill three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));

        // This creature gets +2/+0 for every seven cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                DarkMatterManipulatorValue.instance,
                StaticValue.get(0),
                Duration.WhileOnBattlefield
        ).setText("{this} gets +2/+0 for every seven cards in your graveyard")));
    }

    private DarkMatterManipulator(final DarkMatterManipulator card) {
        super(card);
    }

    @Override
    public DarkMatterManipulator copy() {
        return new DarkMatterManipulator(this);
    }
}

enum DarkMatterManipulatorValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player == null ? 0 : (player.getGraveyard().size() / 7) * 2;
    }

    @Override
    public DarkMatterManipulatorValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
