
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetAndSearchGraveyardHandLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Counterbore extends CardImpl {
    
    public Counterbore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{U}");


        // Counter target spell. 
        // Search its controller's graveyard, hand, and library for all cards with the same name as that spell and exile them. Then that player shuffles their library.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetAndSearchGraveyardHandLibraryEffect().concatBy("."));
    }

    private Counterbore(final Counterbore card) {
        super(card);
    }

    @Override
    public Counterbore copy() {
        return new Counterbore(this);
    }
}
