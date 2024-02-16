
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 *
 */
public final class Ovinize extends CardImpl {

    public Ovinize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target creature loses all abilities and becomes 0/1 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(
                new LoseAllAbilitiesTargetEffect(Duration.EndOfTurn)
                        .setText("Until end of turn, target creature loses all abilities")
        );
        Effect effect = new SetBasePowerToughnessTargetEffect(0, 1, Duration.EndOfTurn);
        effect.setText("and has base power and toughness 0/1");
        this.getSpellAbility().addEffect(effect);
    }

    private Ovinize(final Ovinize card) {
        super(card);
    }

    @Override
    public Ovinize copy() {
        return new Ovinize(this);
    }
}
