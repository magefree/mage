
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Threaten extends CardImpl {

    public Threaten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap target creature"));
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn).setText("and gain control of it until end of turn"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("That creature gains haste until end of turn."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Threaten(final Threaten card) {
        super(card);
    }

    @Override
    public Threaten copy() {
        return new Threaten(this);
    }
}
