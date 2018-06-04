
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 * @author fireshoes
 */
public final class SanctumOfUgin extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("colorless creature card");
    private static final FilterSpell filterSpells = new FilterSpell("colorless spell with converted mana cost 7 or greater");

    static {
        filter.add(new ColorlessPredicate());
        filterSpells.add(new ColorlessPredicate());
        filterSpells.add(new ConvertedManaCostPredicate(ComparisonType.MORE_THAN, 6));
    }

    public SanctumOfUgin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Whenever you cast a colorless spell with converted mana cost 7 or greater, you may sacrifice Sanctum of Ugin.
        // If you do, search your library for a colorless creature card, reveal it, put it into your hand, then shuffle your library.
        Effect effect = new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true);
        effect.setText("search your library for a colorless creature card, reveal it, put it into your hand, then shuffle your library");
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(effect, new SacrificeSourceCost()), filterSpells, false));
    }

    public SanctumOfUgin(final SanctumOfUgin card) {
        super(card);
    }

    @Override
    public SanctumOfUgin copy() {
        return new SanctumOfUgin(this);
    }
}
