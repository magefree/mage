package mage.cards.d;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author awjackson
 */
public final class DreamsGrip extends CardImpl {

    public DreamsGrip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        //Tap target permanent;
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent().withChooseHint("to tap"));

        //or untap target permanent.
        Mode mode = new Mode(new UntapTargetEffect());
        mode.addTarget(new TargetPermanent().withChooseHint("to untap"));
        this.getSpellAbility().addMode(mode);

        // Entwine {1}
        this.addAbility(new EntwineAbility("{1}"));
    }

    private DreamsGrip(final DreamsGrip card) {
        super(card);
    }

    @Override
    public DreamsGrip copy() {
        return new DreamsGrip(this);
    }
}
