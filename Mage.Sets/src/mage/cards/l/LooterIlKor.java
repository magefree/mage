
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LooterIlKor extends CardImpl {

    public LooterIlKor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Whenever Looter il-Kor deals damage to an opponent, draw a card, then discard a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new DrawDiscardControllerEffect()));
    }

    private LooterIlKor(final LooterIlKor card) {
        super(card);
    }

    @Override
    public LooterIlKor copy() {
        return new LooterIlKor(this);
    }
}
