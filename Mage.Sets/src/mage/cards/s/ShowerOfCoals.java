
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author cbt33, LevelX2 (Kirtar's Wrath)
 */
public final class ShowerOfCoals extends CardImpl {

    public ShowerOfCoals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Shower of Coals deals 2 damage to each of up to three target creatures and/or players.
        // Threshold - Shower of Coals deals 4 damage to each of those creatures and/or players instead if seven or more cards are in your graveyard.
        Effect effect = new ConditionalOneShotEffect(new DamageTargetEffect(4),
                new DamageTargetEffect(2),
                new CardsInControllerGraveyardCondition(7),
                "{this} deals 2 damage to each of up to three targets.<br/><br/><i>Threshold</i> &mdash; {this} deals 4 damage to each of those permanents and/or players instead if seven or more cards are in your graveyard.");
        this.getSpellAbility().addTarget(new TargetAnyTarget(0, 3));
        this.getSpellAbility().addEffect(effect);

    }

    public ShowerOfCoals(final ShowerOfCoals card) {
        super(card);
    }

    @Override
    public ShowerOfCoals copy() {
        return new ShowerOfCoals(this);
    }
}
