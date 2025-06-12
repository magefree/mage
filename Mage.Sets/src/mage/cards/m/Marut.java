package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TreasureSpentToCastCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Marut extends CardImpl {

    public Marut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Marut enters the battlefield, if mana from a Treasure was spent to cast it, create a Treasure token for each mana from a Treasure spent to cast it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), MarutValue.instance)
        ).withInterveningIf(TreasureSpentToCastCondition.instance));
    }

    private Marut(final Marut card) {
        super(card);
    }

    @Override
    public Marut copy() {
        return new Marut(this);
    }
}

enum MarutValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return ManaPaidSourceWatcher.getTreasurePaid(sourceAbility.getSourceId(), game);
    }

    @Override
    public MarutValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "mana from a Treasure spent to cast it";
    }

    @Override
    public String toString() {
        return "1";
    }
}
