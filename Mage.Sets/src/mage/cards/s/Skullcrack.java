
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class Skullcrack extends CardImpl {

    public Skullcrack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Players can't gain life this turn. Damage can't be prevented this turn. Skullcrack deals 3 damage to target player.
        this.getSpellAbility().addEffect(new CantGainLifeAllEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn, "Damage can't be prevented this turn"));
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());

    }

    private Skullcrack(final Skullcrack card) {
        super(card);
    }

    @Override
    public Skullcrack copy() {
        return new Skullcrack(this);
    }
}
