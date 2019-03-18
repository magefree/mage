
package mage.cards.k;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public final class KruphixsInsight extends CardImpl {

    private static final FilterCard filter = new FilterCard("up to three enchantment cards");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public KruphixsInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Reveal the top six cards of your library. Put up to three enchantment cards from among them into your hand and the rest of the revealed cards into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(new StaticValue(6), false, new StaticValue(3), filter, Zone.GRAVEYARD, false, true, true,Zone.HAND, false));
    }

    public KruphixsInsight(final KruphixsInsight card) {
        super(card);
    }

    @Override
    public KruphixsInsight copy() {
        return new KruphixsInsight(this);
    }
}
