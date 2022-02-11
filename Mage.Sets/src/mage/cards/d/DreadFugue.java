package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.CleaveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author weirddan455
 */
public final class DreadFugue extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("nonland card from it [with mana value 2 or less]");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public DreadFugue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Cleave {2}{B}
        Ability ability = new CleaveAbility(this, new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND, TargetController.ANY), "{2}{B}");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Target player reveals their hand. Choose a nonland card from it [with mana value 2 or less]. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter, TargetController.ANY));
    }

    private DreadFugue(final DreadFugue card) {
        super(card);
    }

    @Override
    public DreadFugue copy() {
        return new DreadFugue(this);
    }
}
