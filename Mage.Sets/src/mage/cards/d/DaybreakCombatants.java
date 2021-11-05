package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.HasteAbility;
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
public final class DaybreakCombatants extends CardImpl {

    public DaybreakCombatants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Daybreak Combatants enters the battlefield, target creature gets +2/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(2, 0, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DaybreakCombatants(final DaybreakCombatants card) {
        super(card);
    }

    @Override
    public DaybreakCombatants copy() {
        return new DaybreakCombatants(this);
    }
}
