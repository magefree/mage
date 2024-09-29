package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GlimmerToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TunnelSurveyor extends CardImpl {

    public TunnelSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Tunnel Surveyor enters, create a 1/1 white Glimmer enchantment creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GlimmerToken())));
    }

    private TunnelSurveyor(final TunnelSurveyor card) {
        super(card);
    }

    @Override
    public TunnelSurveyor copy() {
        return new TunnelSurveyor(this);
    }
}
