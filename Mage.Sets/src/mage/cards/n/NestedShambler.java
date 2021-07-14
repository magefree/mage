package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NestedShambler extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public NestedShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Nested Shambler dies, create X tapped 1/1 green Squirrel creature tokens, where X is Nested Shambler's power.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(
                new SquirrelToken(), xValue, true, false
        )));
    }

    private NestedShambler(final NestedShambler card) {
        super(card);
    }

    @Override
    public NestedShambler copy() {
        return new NestedShambler(this);
    }
}
