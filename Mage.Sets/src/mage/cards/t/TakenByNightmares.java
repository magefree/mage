package mage.cards.t;

import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TakenByNightmares extends CardImpl {

    public TakenByNightmares(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Exile target creature. If you control an enchantment, scry 2.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ScryEffect(2, false),
                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_ENCHANTMENT)
        ).setText("if you control an enchantment, scry 2"));
    }

    private TakenByNightmares(final TakenByNightmares card) {
        super(card);
    }

    @Override
    public TakenByNightmares copy() {
        return new TakenByNightmares(this);
    }
}
