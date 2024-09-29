

package mage.cards.r;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class ReachThroughMists extends CardImpl {

    public ReachThroughMists (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");
        this.subtype.add(SubType.ARCANE);

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    private ReachThroughMists(final ReachThroughMists card) {
        super(card);
    }

    @Override
    public ReachThroughMists copy() {
        return new ReachThroughMists(this);
    }

}
