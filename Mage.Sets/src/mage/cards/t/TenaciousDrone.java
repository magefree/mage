package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BorgToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class TenaciousDrone extends CardImpl {

    public TenaciousDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature dies, create a 1/1 colorless Borg artifact creature token.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new BorgToken())));
    }

    private TenaciousDrone(final TenaciousDrone card) {
        super(card);
    }

    @Override
    public TenaciousDrone copy() {
        return new TenaciousDrone(this);
    }
}
