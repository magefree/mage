package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.DethroneAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ParkHeightsMaverick extends CardImpl {

    public ParkHeightsMaverick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Dethrone
        this.addAbility(new DethroneAbility());

        // Park Heights Maverick can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Whenever Park Heights Maverick deals combat damage to a player or dies, proliferate.
        this.addAbility(new OrTriggeredAbility(
                Zone.ALL, new ProliferateEffect(false), false,
                "Whenever {this} deals combat damage to a player or dies, ",
                new DealsCombatDamageToAPlayerTriggeredAbility(null, false),
                new DiesSourceTriggeredAbility(null, false)
        ));
    }

    private ParkHeightsMaverick(final ParkHeightsMaverick card) {
        super(card);
    }

    @Override
    public ParkHeightsMaverick copy() {
        return new ParkHeightsMaverick(this);
    }
}
