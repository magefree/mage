
package mage.cards.q;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetAndSearchGraveyardHandLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Quash extends CardImpl {
    
    public Quash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");


        // Counter target instant or sorcery spell. 
        // Search its controller's graveyard, hand, and library for all cards with the same name as that spell and exile them. Then that player shuffles their library.
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
        this.getSpellAbility().addEffect(new CounterTargetAndSearchGraveyardHandLibraryEffect());
    }

    private Quash(final Quash card) {
        super(card);
    }

    @Override
    public Quash copy() {
        return new Quash(this);
    }
}
