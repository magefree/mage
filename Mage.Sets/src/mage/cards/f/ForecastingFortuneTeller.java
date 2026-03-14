package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForecastingFortuneTeller extends CardImpl {

    public ForecastingFortuneTeller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When this creature enters, create a Clue token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ClueArtifactToken())));
    }

    private ForecastingFortuneTeller(final ForecastingFortuneTeller card) {
        super(card);
    }

    @Override
    public ForecastingFortuneTeller copy() {
        return new ForecastingFortuneTeller(this);
    }
}
