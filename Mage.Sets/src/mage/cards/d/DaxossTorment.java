
package mage.cards.d;

import java.util.UUID;

import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 * @author fireshoes
 */
public final class DaxossTorment extends CardImpl {

    public DaxossTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Constellation - Whenever Daxos's Torment or another enchantment enters the battlefield under your control, Daxos's Torment becomes a 5/5 Demon creature with flying and haste until end of turn in addition to its other types.
        this.addAbility(new ConstellationAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(5, 5, "5/5 Demon creature with flying and haste")
                        .withSubType(SubType.DEMON)
                        .withAbility(FlyingAbility.getInstance())
                        .withAbility(HasteAbility.getInstance()),
                CardType.ENCHANTMENT, Duration.EndOfTurn)));
    }

    private DaxossTorment(final DaxossTorment card) {
        super(card);
    }

    @Override
    public DaxossTorment copy() {
        return new DaxossTorment(this);
    }
}
