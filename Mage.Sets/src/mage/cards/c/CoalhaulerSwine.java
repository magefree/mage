package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;

import java.util.UUID;

/**
 * @author vereena42
 */
public final class CoalhaulerSwine extends CardImpl {

    public CoalhaulerSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Coalhauler Swine is dealt damage, it deals that much damage to each player.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new DamagePlayersEffect(
                Outcome.Neutral, CoalhaulerSwineValue.instance, TargetController.ANY, "it"
        ), false, false, true));
    }

    private CoalhaulerSwine(final CoalhaulerSwine card) {
        super(card);
    }

    @Override
    public CoalhaulerSwine copy() {
        return new CoalhaulerSwine(this);
    }
}

enum CoalhaulerSwineValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("damage");
    }

    @Override
    public CoalhaulerSwineValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "that much";
    }
}
