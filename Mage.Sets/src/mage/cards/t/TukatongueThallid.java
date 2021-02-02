

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author Loki
 */
public final class TukatongueThallid extends CardImpl {

    public TukatongueThallid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");

        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new SaprolingToken()), false));
    }

    private TukatongueThallid(final TukatongueThallid card) {
        super(card);
    }

    @Override
    public TukatongueThallid copy() {
        return new TukatongueThallid(this);
    }
}
