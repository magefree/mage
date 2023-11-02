package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class JennyGeneratedAnomaly extends CardImpl {

    public JennyGeneratedAnomaly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Jenny deals combat damage to a player, it explores.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ExploreSourceEffect(), false));
    }

    private JennyGeneratedAnomaly(final JennyGeneratedAnomaly card) {
        super(card);
    }

    @Override
    public JennyGeneratedAnomaly copy() {
        return new JennyGeneratedAnomaly(this);
    }
}
