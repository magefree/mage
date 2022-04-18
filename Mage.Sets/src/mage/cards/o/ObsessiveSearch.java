
package mage.cards.o;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class ObsessiveSearch extends CardImpl {

    public ObsessiveSearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Madness {U}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{U}")));
    }

    private ObsessiveSearch(final ObsessiveSearch card) {
        super(card);
    }

    @Override
    public ObsessiveSearch copy() {
        return new ObsessiveSearch(this);
    }
}
