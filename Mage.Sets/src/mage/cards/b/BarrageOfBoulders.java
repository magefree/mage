package mage.cards.b;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BarrageOfBoulders extends CardImpl {

    public BarrageOfBoulders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Barrage of Boulders deals 1 damage to each creature you don't control.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        // Ferocious - If you control a creature with power 4 or greater, creatures can't block this turn
        Effect effect = new ConditionalRestrictionEffect(
                Duration.EndOfTurn,
                new CantBlockAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn),
                new LockedInCondition(FerociousCondition.instance), null);
        effect.setText("<br/><i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, creatures can't block this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private BarrageOfBoulders(final BarrageOfBoulders card) {
        super(card);
    }

    @Override
    public BarrageOfBoulders copy() {
        return new BarrageOfBoulders(this);
    }
}
