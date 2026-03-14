

package mage.cards.j;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author Loki
 */
public final class JadeIdol extends CardImpl {

    public JadeIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(
                    4, 4, "4/4 Spirit artifact creature", SubType.SPIRIT
                ).withType(CardType.ARTIFACT),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ),
            StaticFilters.FILTER_SPELL_SPIRIT_OR_ARCANE,
            false
        ));
    }

    private JadeIdol(final JadeIdol card) {
        super(card);
    }

    @Override
    public JadeIdol copy() {
        return new JadeIdol(this);
    }
}
