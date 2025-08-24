package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.common.TwoTappedCreaturesCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamiShipsEngineer extends CardImpl {

    public SamiShipsEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if you control two or more tapped creatures, create a tapped 2/2 colorless Robot artifact creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new RobotToken(), 1, true)
        ).withInterveningIf(TwoTappedCreaturesCondition.instance).addHint(TwoTappedCreaturesCondition.getHint()));
    }

    private SamiShipsEngineer(final SamiShipsEngineer card) {
        super(card);
    }

    @Override
    public SamiShipsEngineer copy() {
        return new SamiShipsEngineer(this);
    }
}
