
package mage.cards.s;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author North
 */
public final class Shadowfeed extends CardImpl {

    public Shadowfeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Exile target card from a graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());

        // You gain 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
    }

    private Shadowfeed(final Shadowfeed card) {
        super(card);
    }

    @Override
    public Shadowfeed copy() {
        return new Shadowfeed(this);
    }
}
