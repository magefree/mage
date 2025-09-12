package mage.cards.i;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class InvigoratedRampage extends CardImpl {

    public InvigoratedRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one 
        // Target creature gets +4/+0 and gains trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 0).setText("target creature gets +4/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Two target creatures each get +2/+0 and gain trample until end of turn.
        Mode mode = new Mode(new BoostTargetEffect(2, 0).setText("two target creatures each get +2/+0"));
        mode.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("and gain trample until end of turn"));
        mode.addTarget(new TargetCreaturePermanent(2));
        this.getSpellAbility().addMode(mode);
    }

    private InvigoratedRampage(final InvigoratedRampage card) {
        super(card);
    }

    @Override
    public InvigoratedRampage copy() {
        return new InvigoratedRampage(this);
    }
}
