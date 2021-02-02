package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class ForceProtection extends CardImpl {

    public ForceProtection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        

        // Target creature you control gains protection from color of your choice until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent()); 
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private ForceProtection(final ForceProtection card) {
        super(card);
    }

    @Override
    public ForceProtection copy() {
        return new ForceProtection(this);
    }
}
