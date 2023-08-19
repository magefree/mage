package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YasminKhan extends CardImpl {

    public YasminKhan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}: Exile the top card of your library. Until your next end step, you may play it.
        this.addAbility(new SimpleActivatedAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(
                1, false, Duration.UntilYourNextEndStep
        ), new TapSourceCost()));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private YasminKhan(final YasminKhan card) {
        super(card);
    }

    @Override
    public YasminKhan copy() {
        return new YasminKhan(this);
    }
}
