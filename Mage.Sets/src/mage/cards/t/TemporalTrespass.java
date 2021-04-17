
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class TemporalTrespass extends CardImpl {

    public TemporalTrespass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{8}{U}{U}{U}");

        // Delve
        this.addAbility(new DelveAbility());
        // Take an extra turn after this one. Exile Temporal Trespass.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private TemporalTrespass(final TemporalTrespass card) {
        super(card);
    }

    @Override
    public TemporalTrespass copy() {
        return new TemporalTrespass(this);
    }
}
