
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PenumbraKavuToken;

/**
 *
 * @author Loki
 */
public final class PenumbraKavu extends CardImpl {

    public PenumbraKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PenumbraKavuToken(), 1), false));
    }

    private PenumbraKavu(final PenumbraKavu card) {
        super(card);
    }

    @Override
    public PenumbraKavu copy() {
        return new PenumbraKavu(this);
    }
}
