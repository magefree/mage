package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArtfulTakedown extends CardImpl {

    private static final FilterCreaturePermanent filter1
            = new FilterCreaturePermanent("creature (to tap)");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("creature (to shrink)");

    public ArtfulTakedown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{B}");

        // Choose one or both —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // • Tap target creature.
        this.getSpellAbility().addEffect(
                new TapTargetEffect("tap target creature")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter1).withChooseHint("tap"));

        // • Target creature gets -2/-4 until end of turn.
        Mode mode = new Mode(
                new BoostTargetEffect(-2, -4, Duration.EndOfTurn)
                        .setText("target creature gets -2/-4 until end of turn")
        );
        mode.addTarget(new TargetCreaturePermanent(filter2).withChooseHint("gets -2/-4 until end of turn"));
        this.getSpellAbility().addMode(mode);
    }

    private ArtfulTakedown(final ArtfulTakedown card) {
        super(card);
    }

    @Override
    public ArtfulTakedown copy() {
        return new ArtfulTakedown(this);
    }
}
