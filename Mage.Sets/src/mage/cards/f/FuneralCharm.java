
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class FuneralCharm extends CardImpl {

    public FuneralCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Choose one - Target player discards a card; or target creature gets +2/-1 until end of turn; or target creature gains swampwalk until end of turn.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        Mode mode = new Mode(new BoostTargetEffect(2, -1, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        mode = new Mode(new GainAbilityTargetEffect(new SwampwalkAbility(), Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private FuneralCharm(final FuneralCharm card) {
        super(card);
    }

    @Override
    public FuneralCharm copy() {
        return new FuneralCharm(this);
    }
}
