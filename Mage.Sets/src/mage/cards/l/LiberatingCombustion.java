
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LiberatingCombustion extends CardImpl {

    private final static FilterCard filter = new FilterCard("Chandra, Pyrogenius");

    static {
        filter.add(new NamePredicate("Chandra, Pyrogenius"));
    }

    public LiberatingCombustion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Liberating Combustion deals 6 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // You may search your library and/or graveyard for a card named Chandra, Pyrogenius, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter));
    }

    public LiberatingCombustion(final LiberatingCombustion card) {
        super(card);
    }

    @Override
    public LiberatingCombustion copy() {
        return new LiberatingCombustion(this);
    }
}
