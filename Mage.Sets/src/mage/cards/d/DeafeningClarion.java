package mage.cards.d;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class DeafeningClarion extends CardImpl {

    public DeafeningClarion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{W}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Deafening Clarion deals 3 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(
                3, StaticFilters.FILTER_PERMANENT_CREATURE
        ));

        // • Creatures you control gain lifelink until end of turn.
        Mode mode = new Mode(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        this.getSpellAbility().addMode(mode);
    }

    private DeafeningClarion(final DeafeningClarion card) {
        super(card);
    }

    @Override
    public DeafeningClarion copy() {
        return new DeafeningClarion(this);
    }
}
