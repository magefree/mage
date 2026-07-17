
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author Loki
 */
public final class HalcyonGlaze extends CardImpl {

    public HalcyonGlaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // Whenever you cast a creature spell, Halcyon Glaze becomes a 4/4 Illusion creature with flying in addition to its other types until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4, "4/4 Illusion creature with flying", SubType.ILLUSION)
                    .withAbility(FlyingAbility.getInstance()),
                CardType.ENCHANTMENT,
                Duration.EndOfTurn
            ),
            StaticFilters.FILTER_SPELL_A_CREATURE,
            false
        ));
    }

    private HalcyonGlaze(final HalcyonGlaze card) {
        super(card);
    }

    @Override
    public HalcyonGlaze copy() {
        return new HalcyonGlaze(this);
    }
}
