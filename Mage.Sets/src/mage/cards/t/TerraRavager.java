
package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TerraRavager extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("lands defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public TerraRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever Terra Ravager attacks, it gets +X/+0 until end of turn, where X is the number of lands defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.get(0), Duration.EndOfTurn, true, "it"
        ), false));
    }

    private TerraRavager(final TerraRavager card) {
        super(card);
    }

    @Override
    public TerraRavager copy() {
        return new TerraRavager(this);
    }
}
