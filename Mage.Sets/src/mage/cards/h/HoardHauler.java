package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author weirddan455
 */
public final class HoardHauler extends CardImpl {

    public HoardHauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Hoard Hauler deals combat damage to a player, create a Treasure token for each artifact they control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new TreasureToken(), HoardHaulerValue.instance), false, true));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private HoardHauler(final HoardHauler card) {
        super(card);
    }

    @Override
    public HoardHauler copy() {
        return new HoardHauler(this);
    }
}

enum HoardHaulerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_ARTIFACT, effect.getTargetPointer().getFirst(game, sourceAbility), game);
    }

    @Override
    public HoardHaulerValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "artifact they control";
    }
}
