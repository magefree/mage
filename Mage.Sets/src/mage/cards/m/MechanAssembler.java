package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MechanAssembler extends CardImpl {

    public MechanAssembler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever another artifact you control enters, create a 2/2 colorless Robot artifact creature token. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new RobotToken()), StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT
        ).setTriggersLimitEachTurn(1));
    }

    private MechanAssembler(final MechanAssembler card) {
        super(card);
    }

    @Override
    public MechanAssembler copy() {
        return new MechanAssembler(this);
    }
}
