package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
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
public final class OldGnawbone extends CardImpl {

    public OldGnawbone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a creature you control deals combat damage to a player, create that many Treasure tokens.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), OldGnawboneValue.instance),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE,
                false, SetTargetPointer.NONE, true
        ));
    }

    private OldGnawbone(final OldGnawbone card) {
        super(card);
    }

    @Override
    public OldGnawbone copy() {
        return new OldGnawbone(this);
    }
}

enum OldGnawboneValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Integer damage = (Integer) effect.getValue("damage");
        if (damage != null) {
            return damage;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "that many";
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public OldGnawboneValue copy() {
        return OldGnawboneValue.instance;
    }
}
