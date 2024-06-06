package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessPyrosurfer extends CardImpl {

    public RecklessPyrosurfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Landfall -- Whenever a land enters the battlefield under your control, Reckless Pyrosurfer gains battle cry until end of turn.
        this.addAbility(new LandfallAbility(new GainAbilitySourceEffect(new BattleCryAbility(), Duration.EndOfTurn)));
    }

    private RecklessPyrosurfer(final RecklessPyrosurfer card) {
        super(card);
    }

    @Override
    public RecklessPyrosurfer copy() {
        return new RecklessPyrosurfer(this);
    }
}
