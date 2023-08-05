package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CovetedPrize extends CardImpl {

    private static final FilterCard filter = new FilterCard("a spell with mana value 4 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    public CovetedPrize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // This spell costs {1} less to cast for each creature in your party.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, PartyCount.instance)
        ).addHint(PartyCountHint.instance).setRuleAtTheTop(true));

        // Search your library for a card, put it into your hand, then shuffle your library. If you have a full party, you may cast a spell with converted mana cost 4 or less from your hand without paying its mana cost.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(), false));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CastFromHandForFreeEffect(filter),
                FullPartyCondition.instance, "If you have a full party, " +
                "you may cast a spell with mana value 4 or less from your hand without paying its mana cost."
        ));
    }

    private CovetedPrize(final CovetedPrize card) {
        super(card);
    }

    @Override
    public CovetedPrize copy() {
        return new CovetedPrize(this);
    }
}
