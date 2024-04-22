package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class Retaliation extends CardImpl {

    public Retaliation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Creatures you control have "Whenever this creature becomes blocked by a creature, 
        // this creature gets +1/+1 until end of turn."
        Effect effect = new GainAbilityControlledEffect(
                new BecomesBlockedByCreatureTriggeredAbility(
                        new BoostSourceEffect(
                                1, 1,
                                Duration.EndOfTurn), false),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("Creatures you control have \"Whenever this creature becomes blocked by a creature, "
                + "this creature gets +1/+1 until end of turn.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                effect));
    }

    private Retaliation(final Retaliation card) {
        super(card);
    }

    @Override
    public Retaliation copy() {
        return new Retaliation(this);
    }
}
