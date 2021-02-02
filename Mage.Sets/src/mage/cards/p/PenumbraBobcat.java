
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PenumbraBobcatToken;

/**
 *
 * @author Loki
 */
public final class PenumbraBobcat extends CardImpl {

    public PenumbraBobcat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PenumbraBobcatToken(), 1), false));
    }

    private PenumbraBobcat(final PenumbraBobcat card) {
        super(card);
    }

    @Override
    public PenumbraBobcat copy() {
        return new PenumbraBobcat(this);
    }
}
