
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Mobilize extends CardImpl {
    
    private static final String rule = "untap all creatures you control";

    public Mobilize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");

        // Untap all creatures you control.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), rule));
    }

    private Mobilize(final Mobilize card) {
        super(card);
    }

    @Override
    public Mobilize copy() {
        return new Mobilize(this);
    }
}
