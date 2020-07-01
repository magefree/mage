package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaybreakCharger extends CardImpl {

    public DaybreakCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Daybreak Charger enters the battlefield, target creature gets +2/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(2, 0, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DaybreakCharger(final DaybreakCharger card) {
        super(card);
    }

    @Override
    public DaybreakCharger copy() {
        return new DaybreakCharger(this);
    }
}
