package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwirlingTorrent extends CardImpl {

    public SwirlingTorrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Put target creature on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("put on library top"));

        // • Return target creature to its owner's hand.
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetCreaturePermanent().withChooseHint("return to hand"));
        this.getSpellAbility().addMode(mode);
    }

    private SwirlingTorrent(final SwirlingTorrent card) {
        super(card);
    }

    @Override
    public SwirlingTorrent copy() {
        return new SwirlingTorrent(this);
    }
}
