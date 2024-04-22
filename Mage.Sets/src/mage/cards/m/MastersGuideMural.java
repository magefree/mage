package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GolemWhiteBlueToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MastersGuideMural extends CardImpl {

    public MastersGuideMural(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}{U}");
        this.secondSideCardClazz = mage.cards.m.MastersManufactory.class;

        // When Master's Guide-Mural enters the battlefield, create a 4/4 white and blue Golem artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemWhiteBlueToken())));

        // Craft with artifact {4}{W}{W}{U}
        this.addAbility(new CraftAbility("{4}{W}{W}{U}"));
    }

    private MastersGuideMural(final MastersGuideMural card) {
        super(card);
    }

    @Override
    public MastersGuideMural copy() {
        return new MastersGuideMural(this);
    }
}
