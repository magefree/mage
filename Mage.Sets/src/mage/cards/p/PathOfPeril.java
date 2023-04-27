package mage.cards.p;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PathOfPeril extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures [with mana value 2 or less]");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public PathOfPeril(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Cleave {4}{W}{B}
        this.addAbility(new CleaveAbility(
                this, new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES), "{4}{W}{B}"));

        // Destroy all creatures [with mana value 2 or less].
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private PathOfPeril(final PathOfPeril card) {
        super(card);
    }

    @Override
    public PathOfPeril copy() {
        return new PathOfPeril(this);
    }
}
