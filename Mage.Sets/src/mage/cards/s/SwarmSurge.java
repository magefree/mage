
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author LevelX2
 */
public final class SwarmSurge extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("Colorless creatures you control");

    static {
        FILTER.add(ColorlessPredicate.instance);
    }

    public SwarmSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));

        // Colorless creatures you control also gain first strike until end of turn.
        Effect effect = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, FILTER);
        effect.setText("Colorless creatures you control also gain first strike until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private SwarmSurge(final SwarmSurge card) {
        super(card);
    }

    @Override
    public SwarmSurge copy() {
        return new SwarmSurge(this);
    }
}
