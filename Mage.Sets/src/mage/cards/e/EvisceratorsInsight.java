package mage.cards.e;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EvisceratorsInsight extends CardImpl {

    public EvisceratorsInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");
        
        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));

        // Flashback {4}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{4}{B}")));
    }

    private EvisceratorsInsight(final EvisceratorsInsight card) {
        super(card);
    }

    @Override
    public EvisceratorsInsight copy() {
        return new EvisceratorsInsight(this);
    }
}
