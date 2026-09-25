package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.dynamicvalue.common.TargetPermanentPowerCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class ArniRenownedChampion extends CardImpl {

    public ArniRenownedChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another creature you control enters, Arni gets +X/+0 until end of turn, where X is that creature's power.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
            Zone.BATTLEFIELD,
            new BoostSourceEffect(TargetPermanentPowerCount.instance, StaticValue.get(0), Duration.EndOfTurn),
            StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false, SetTargetPointer.PERMANENT
        ));
    }

    private ArniRenownedChampion(final ArniRenownedChampion card) {
        super(card);
    }

    @Override
    public ArniRenownedChampion copy() {
        return new ArniRenownedChampion(this);
    }
}
