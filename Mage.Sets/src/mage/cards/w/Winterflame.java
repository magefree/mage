package mage.cards.w;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Winterflame extends CardImpl {

    public Winterflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");


        // Choose one or both -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);
        // * Tap target creature
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("tap"));
        // * Winterflame deals 2 damage to target creature
        Mode mode = new Mode(new DamageTargetEffect(2));
        mode.addTarget(new TargetCreaturePermanent().withChooseHint("deals 2 damage to"));
        this.getSpellAbility().addMode(mode);

    }

    private Winterflame(final Winterflame card) {
        super(card);
    }

    @Override
    public Winterflame copy() {
        return new Winterflame(this);
    }
}
