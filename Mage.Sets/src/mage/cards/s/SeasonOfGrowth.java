package mage.cards.s;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.other.TargetsPermanentPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeasonOfGrowth extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that targets a creature you control");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public SeasonOfGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Whenever a creature enters the battlefield under your control, scry 1.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new ScryEffect(1), StaticFilters.FILTER_PERMANENT_CREATURE_A
        ));

        // Whenever you cast a spell that targets a creature you control, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private SeasonOfGrowth(final SeasonOfGrowth card) {
        super(card);
    }

    @Override
    public SeasonOfGrowth copy() {
        return new SeasonOfGrowth(this);
    }
}
