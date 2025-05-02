package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.OpponentDealtNoncombatDamageTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildfireElemental extends CardImpl {

    public WildfireElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent is dealt noncombat damage, creatures you control get +1/+0 until end of turn.
        this.addAbility(new OpponentDealtNoncombatDamageTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn)));
    }

    private WildfireElemental(final WildfireElemental card) {
        super(card);
    }

    @Override
    public WildfireElemental copy() {
        return new WildfireElemental(this);
    }
}
