
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author TheElk801
 */
public final class SylvanAwakening extends CardImpl {

    public SylvanAwakening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Until your next turn, all lands you control become 2/2 Elemental creatures with reach, indestructible, and haste. They're still lands.
        this.getSpellAbility().addEffect(new BecomesCreatureAllEffect(
                new CreatureToken(2, 2, "2/2 Elemental creatures with reach, indestructible, and haste")
                .withSubType(SubType.ELEMENTAL)
                .withAbility(ReachAbility.getInstance())
                .withAbility(IndestructibleAbility.getInstance())
                .withAbility(HasteAbility.getInstance()),
                "lands",
                new FilterControlledLandPermanent("all lands you control"),
                Duration.UntilYourNextTurn,
                false)
        );
    }

    public SylvanAwakening(final SylvanAwakening card) {
        super(card);
    }

    @Override
    public SylvanAwakening copy() {
        return new SylvanAwakening(this);
    }
}
