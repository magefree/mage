
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class NayaCharm extends CardImpl {

    public NayaCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{G}{W}");

        // Choose one - Naya Charm deals 3 damage to target creature;
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // or return target card from a graveyard to its owner's hand;
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addMode(mode);
        // or tap all creatures target player controls.
        mode = new Mode(new TapAllTargetPlayerControlsEffect(FILTER_PERMANENT_CREATURES));
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private NayaCharm(final NayaCharm card) {
        super(card);
    }

    @Override
    public NayaCharm copy() {
        return new NayaCharm(this);
    }
}
