

package mage.cards.v;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class Vindicate extends CardImpl {

    public Vindicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{B}");


        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private Vindicate(final Vindicate card) {
        super(card);
    }

    @Override
    public Vindicate copy() {
        return new Vindicate(this);
    }

}
