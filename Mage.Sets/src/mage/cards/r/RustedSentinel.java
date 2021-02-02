

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class RustedSentinel extends CardImpl {

    public RustedSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private RustedSentinel(final RustedSentinel card) {
        super(card);
    }

    @Override
    public RustedSentinel copy() {
        return new RustedSentinel(this);
    }

}
