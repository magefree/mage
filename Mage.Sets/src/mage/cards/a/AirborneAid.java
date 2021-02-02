
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author LoneFox
 */
public final class AirborneAid extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Bird on the battlefield");

    static {
        filter.add(SubType.BIRD.getPredicate());
    }

    public AirborneAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Draw a card for each Bird on the battlefield.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    private AirborneAid(final AirborneAid card) {
        super(card);
    }

    @Override
    public AirborneAid copy() {
        return new AirborneAid(this);
    }
}
