
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiscardedByOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author dustinconrad
 */
public final class GuerrillaTactics extends CardImpl {

    public GuerrillaTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Guerrilla Tactics deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // When a spell or ability an opponent controls causes you to discard Guerrilla Tactics, Guerrilla Tactics deals 4 damage to any target.
        Ability ability = new DiscardedByOpponentTriggeredAbility(new DamageTargetEffect(4));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GuerrillaTactics(final GuerrillaTactics card) {
        super(card);
    }

    @Override
    public GuerrillaTactics copy() {
        return new GuerrillaTactics(this);
    }
}
