package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class EnergyArc extends CardImpl {

    public EnergyArc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}");

        // Untap any number of target creatures. Prevent all combat damage that would be dealt to and dealt by those creatures this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap any number of target creatures"));
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true).setText("Prevent all combat damage that would be dealt to"));
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, true).setText("and dealt by those creatures this turn."));
    }

    private EnergyArc(final EnergyArc card) {
        super(card);
    }

    @Override
    public EnergyArc copy() {
        return new EnergyArc(this);
    }
}
