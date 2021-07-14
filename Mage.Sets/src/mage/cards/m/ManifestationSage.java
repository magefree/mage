package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ManifestationSage extends CardImpl {

    public ManifestationSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/U}{G/U}{G/U}{G/U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Manifestation Sage enters the battlefield, create a 0/0 green and blue Fractal creature token. Put X +1/+1 counters on it, where X is the number of cards in your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(FractalToken.getEffect(
                CardsInControllerHandCount.instance, "Put X +1/+1 counters on it, " +
                        "where X is the number of cards in your hand"
        )));
    }

    private ManifestationSage(final ManifestationSage card) {
        super(card);
    }

    @Override
    public ManifestationSage copy() {
        return new ManifestationSage(this);
    }
}
