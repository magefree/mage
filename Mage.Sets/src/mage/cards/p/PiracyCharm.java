
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author dustinconrad
 */
public final class PiracyCharm extends CardImpl {

    public PiracyCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Choose one - Target creature gains islandwalk until end of turn;
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new IslandwalkAbility(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // or target creature gets +2/-1 until end of turn;
        Mode mode = new Mode(new BoostTargetEffect(2, -1, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
        // or target player discards a card.
        mode = new Mode(new DiscardTargetEffect(1));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private PiracyCharm(final PiracyCharm card) {
        super(card);
    }

    @Override
    public PiracyCharm copy() {
        return new PiracyCharm(this);
    }
}
