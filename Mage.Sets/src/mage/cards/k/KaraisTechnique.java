package mage.cards.k;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class KaraisTechnique extends CardImpl {

    public KaraisTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{B}");

        // Sneak {W}{B}
        this.addAbility(new SneakAbility(this, "{W}{B}"));

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("gets +3/+3 until end of turn"));
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3));

        // * Target creature gets -3/-3 until end of turn.
        Mode mode2 = new Mode(new BoostTargetEffect(-3, -3));
        mode2.addTarget(new TargetCreaturePermanent().withChooseHint("gets -3/-3 until end of turn"));
        this.getSpellAbility().addMode(mode2);
    }

    private KaraisTechnique(final KaraisTechnique card) {
        super(card);
    }

    @Override
    public KaraisTechnique copy() {
        return new KaraisTechnique(this);
    }
}
