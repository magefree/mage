package mage.cards.u;

import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndercitysEmbrace extends CardImpl {

    public UndercitysEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target opponent sacrifices a creature. If you control a creature with power 4 or greater, you gain 4 life.
        this.getSpellAbility().addEffect(new SacrificeEffect(
                StaticFilters.FILTER_PERMANENT_A_CREATURE, 1, "target opponent"
        ));
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(4), FerociousCondition.instance,
                "If you control a creature with power 4 or greater, you gain 4 life."
        ));
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private UndercitysEmbrace(final UndercitysEmbrace card) {
        super(card);
    }

    @Override
    public UndercitysEmbrace copy() {
        return new UndercitysEmbrace(this);
    }
}
