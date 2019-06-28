package mage.cards.l;

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
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LavakinBrawler extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.ELEMENTAL);
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public LavakinBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Lavakin Brawler attacks, it gets +1/+0 until end of turn for each Elemental you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, StaticValue.getZeroValue(), Duration.EndOfTurn, true
        ), false));
    }

    private LavakinBrawler(final LavakinBrawler card) {
        super(card);
    }

    @Override
    public LavakinBrawler copy() {
        return new LavakinBrawler(this);
    }
}
