
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author noxx
 */
public final class MomentaryBlink extends CardImpl {

    public MomentaryBlink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Exile target creature you control, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect());
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderYourControlTargetEffect(true));

        // Flashback {3}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{3}{U}"), TimingRule.INSTANT));
    }

    public MomentaryBlink(final MomentaryBlink card) {
        super(card);
    }

    @Override
    public MomentaryBlink copy() {
        return new MomentaryBlink(this);
    }
}
