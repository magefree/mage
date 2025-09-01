package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RemnantElemental extends CardImpl {

    public RemnantElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Landfall -- Whenever a land you control enters, this creature gets +2/+0 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn)));
    }

    private RemnantElemental(final RemnantElemental card) {
        super(card);
    }

    @Override
    public RemnantElemental copy() {
        return new RemnantElemental(this);
    }
}
