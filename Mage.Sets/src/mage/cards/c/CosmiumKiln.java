package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.GnomeToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CosmiumKiln extends CardImpl {

    public CosmiumKiln(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.nightCard = true;
        this.color.setWhite(true);

        // When Cosmium Kiln enters the battlefield, create two 1/1 colorless Gnome artifact creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GnomeToken(), 2)));

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    private CosmiumKiln(final CosmiumKiln card) {
        super(card);
    }

    @Override
    public CosmiumKiln copy() {
        return new CosmiumKiln(this);
    }
}
