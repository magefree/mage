
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class PartTheWaterveil extends CardImpl {

    public PartTheWaterveil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}{U}");

        // Take an extra turn after this one. Exile Part the Waterveil.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());

        // Awaken 6-{6}{U}{U}{U}
        this.addAbility(new AwakenAbility(this, 6, "{6}{U}{U}{U}"));
    }

    private PartTheWaterveil(final PartTheWaterveil card) {
        super(card);
    }

    @Override
    public PartTheWaterveil copy() {
        return new PartTheWaterveil(this);
    }
}
