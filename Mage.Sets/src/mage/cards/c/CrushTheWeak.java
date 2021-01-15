package mage.cards.c;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrushTheWeak extends CardImpl {

    public CrushTheWeak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Crush the Weak deals 2 damage to each creature. If a creature dealt damage this way would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this, Duration.EndOfTurn));
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));

        // Foretell {R}
        this.addAbility(new ForetellAbility(this, "{R}"));
    }

    private CrushTheWeak(final CrushTheWeak card) {
        super(card);
    }

    @Override
    public CrushTheWeak copy() {
        return new CrushTheWeak(this);
    }
}
